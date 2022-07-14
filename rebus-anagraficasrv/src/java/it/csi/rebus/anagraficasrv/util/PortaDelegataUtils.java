/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.anagraficasrv.util;

import javax.servlet.http.HttpServletRequest;

import it.csi.csi.porte.InfoPortaDelegata;
import it.csi.csi.porte.proxy.PDProxy;
import it.csi.csi.util.xml.PDConfigReader;
import it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService;

public class PortaDelegataUtils
{
  protected static InfoPortaDelegata _infoPortaDelegataPEP = null;

  public static PolicyEnforcerBaseService getPolicyEnforcerPDProxy(HttpServletRequest request) throws Exception
  {
    String pepAttributeName = PolicyEnforcerBaseService.class.getName();
    PolicyEnforcerBaseService policyEnforcerBaseService = null;
    if (request != null)
    {
      policyEnforcerBaseService = (PolicyEnforcerBaseService) request.getAttribute(pepAttributeName);
    }
    if (policyEnforcerBaseService == null)
    {
      policyEnforcerBaseService = createPEP();
      if (request != null)
      {
        request.setAttribute(pepAttributeName,policyEnforcerBaseService);
      }
    }
    return policyEnforcerBaseService;
  }

  private static synchronized PolicyEnforcerBaseService createPEP() throws Exception
  {
    if (_infoPortaDelegataPEP == null)
    {
      loadPDConfig();
    }
    return (PolicyEnforcerBaseService) PDProxy.newInstance(_infoPortaDelegataPEP);
  }

  private static synchronized void loadPDConfig() throws Exception
  {
    if (_infoPortaDelegataPEP == null)
    {
      _infoPortaDelegataPEP = PDConfigReader.read(PortaDelegataUtils.class.getClassLoader().getResourceAsStream("/it/csi/rebus/anagraficasrv/xml/pdIride2.xml"));
    }
  }
}
