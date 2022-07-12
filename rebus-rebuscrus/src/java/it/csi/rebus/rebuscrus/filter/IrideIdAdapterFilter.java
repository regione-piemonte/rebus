/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.csi.rebus.rebuscrus.business.facade.IrideServFacade;
import it.csi.rebus.rebuscrus.business.service.UserService;
import it.csi.rebus.rebuscrus.common.UserMock;
import it.csi.rebus.rebuscrus.integration.iride.Application;
import it.csi.rebus.rebuscrus.integration.iride.Identita;
import it.csi.rebus.rebuscrus.integration.iride.MalformedIdTokenException;
import it.csi.rebus.rebuscrus.integration.iride.Ruolo;
import it.csi.rebus.rebuscrus.integration.iride.UseCase;
import it.csi.rebus.rebuscrus.security.AuthorizationRoles;
import it.csi.rebus.rebuscrus.security.UserInfo;

/**
 * Inserisce in sessione:
 * <ul>
 * <li>l'identit&agrave; digitale relativa all'utente autenticato.
 * <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter implements Filter {

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

	public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

	public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";

	@Autowired
	private UserService userService;
	@Autowired
	private IrideServFacade irideServFacade;

	/**  */
	protected static final Logger LOG = Logger.getLogger(it.csi.rebus.rebuscrus.util.Constants.COMPONENT_NAME + ".security");

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fchn) throws IOException, ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null) {

			String marker = getToken(hreq);
			Identita identita = null;
			if (devmode) {
				try {
					identita = irideServFacade.identificaUserPassword(UserMock.DEMO20, "***********");
					marker = identita.getCodFiscale();
				} catch (Exception e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}

			}
			if (marker != null) {
				try {
					if (identita == null)
						identita = irideServFacade.getIdentitaFromToken(normalizeToken(marker));
					LOG.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
					hreq.getSession().setAttribute(IRIDE_ID_SESSIONATTR, identita);
					UserInfo userInfo = new UserInfo();
					userInfo.setIdentita(identita);
					userInfo.setNome(identita.getNome());
					userInfo.setCognome(identita.getCognome());
					userInfo.setEnte("--");
					userInfo.setCodFisc(identita.getCodFiscale());
					userInfo.setIdIride(identita.toString());
					//userInfo.setIdEnte((long)9988);
					Application application = new Application("REBUS");
					Ruolo[] ruoli = irideServFacade.findRuoliForPersonaInApplication(identita, application);

					if (ruoli.length > 0) {
						if (ruoli.length == 1) {
							userInfo.setRuolo(ruoli[0]);
						} else {
							userInfo.setRuoli(Arrays.asList(ruoli));
							// userInfo.setRuolo(ruoli[0]);
						}
					}

					try {
						UseCase[] useCases = irideServFacade.findUseCasesForPersonaInApplication(identita, "REBUS");
						List<String> authority = new ArrayList<>();
						authority.add(AuthorizationRoles.UTENTE.getId());
						if (useCases != null && useCases.length > 0) {
							if(ruoli.length > 1) {
								userInfo.setUsecase(Arrays.asList(useCases));
							}
							for (UseCase u : useCases) {
								if(ruoli.length == 1) {
									Ruolo[] ruoliTmp = irideServFacade.findRuoliForPersonaInUseCase(identita, u);
									for(Ruolo ruoloTmp:ruoliTmp) {
										if(ruoloTmp.getCodiceRuolo().equals(ruoli[0].getCodiceRuolo())) {
											authority.add(u.getId());
										}
									}
								}
								else {
								authority.add(u.getId());
								}
							LOG.info("use cases:" + authority);
							/*
							 * if ("DEMO 30".equals(identita.getCognome())) {
							 * userInfo.setRuolo("CONSULTATORE_REBUS"); }
							 */
							}
						}
						userInfo.setAuthority(authority);
						userInfo = userService.getDettaglioFunzionario(userInfo);
					} catch (Exception e) {
						LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
					}

					hreq.getSession().setAttribute(USERINFO_SESSIONATTR, userInfo);
				} catch (MalformedIdTokenException e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				} catch (Exception e) {
					LOG.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}
			} else {
				// il marcatore deve sempre essere presente altrimenti e' una
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					LOG.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
				}
			}
		}

		fchn.doFilter(req, resp);

	}
	

	private boolean mustCheckPage(String requestURI) {
		if (requestURI != null)
			return true;
		else
			return false;
	}

	public void destroy() {
		// NOP
	}

	private static final String DEVMODE_INIT_PARAM = "devmode";
	private boolean devmode = true;

	public void init(FilterConfig fc) throws ServletException {
		String sDevmode = fc.getInitParameter(DEVMODE_INIT_PARAM);
		if ("true".equals(sDevmode)) {
			devmode = true;
		} else {
			devmode = false;
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, fc.getServletContext());
	}

	public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(AUTH_ID_MARKER);
		if (marker == null && devmode) {
			return getTokenDevMode(httpreq);
		} else {
			return marker;
		}
	}

	private String getTokenDevMode(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getParameter(AUTH_ID_MARKER);
		return marker;
	}

	private String normalizeToken(String token) {
		return token;
	}

}
