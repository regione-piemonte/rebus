/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.rebus.rebuscrus.integration.iride;

public class PolicyEnforcerBaseServiceProxy implements it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseService {
  private String _endpoint = null;
  private it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseService policyEnforcerBaseService = null;
  
  public PolicyEnforcerBaseServiceProxy() {
    _initPolicyEnforcerBaseServiceProxy();
  }
  
  public PolicyEnforcerBaseServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initPolicyEnforcerBaseServiceProxy();
  }
  
  private void _initPolicyEnforcerBaseServiceProxy() {
    try {
      policyEnforcerBaseService = (new it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseServiceServiceLocator()).getPolicyEnforcerBase();
      if (policyEnforcerBaseService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)policyEnforcerBaseService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)policyEnforcerBaseService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (policyEnforcerBaseService != null)
      ((javax.xml.rpc.Stub)policyEnforcerBaseService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.PolicyEnforcerBaseService getPolicyEnforcerBaseService() {
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService;
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Identita identificaUserPassword(java.lang.String in0, java.lang.String in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.AuthException, it.csi.rebus.rebuscrus.integration.iride.MalformedUsernameException, it.csi.rebus.rebuscrus.integration.iride.IdProviderNotFoundException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.identificaUserPassword(in0, in1);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Identita identificaCertificato(byte[] in0) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.CertRevokedException, it.csi.rebus.rebuscrus.integration.iride.IdProviderNotFoundException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.CertOutsideValidityException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.identificaCertificato(in0);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Identita identificaUserPasswordPIN(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.AuthException, it.csi.rebus.rebuscrus.integration.iride.MalformedUsernameException, it.csi.rebus.rebuscrus.integration.iride.IdProviderNotFoundException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.identificaUserPasswordPIN(in0, in1, in2);
  }
  
  public boolean isPersonaAutorizzataInUseCase(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.UseCase in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchUseCaseException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.isPersonaAutorizzataInUseCase(in0, in1);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.UseCase[] findUseCasesForPersonaInApplication(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.Application in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.findUseCasesForPersonaInApplication(in0, in1);
  }
  
  public boolean isIdentitaAutentica(it.csi.rebus.rebuscrus.integration.iride.Identita in0) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.isIdentitaAutentica(in0);
  }
  
  public java.lang.String getInfoPersonaInUseCase(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.UseCase in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchUseCaseException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.getInfoPersonaInUseCase(in0, in1);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Ruolo[] findRuoliForPersonaInUseCase(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.UseCase in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchUseCaseException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.findRuoliForPersonaInUseCase(in0, in1);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Ruolo[] findRuoliForPersonaInApplication(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.Application in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.findRuoliForPersonaInApplication(in0, in1);
  }
  
  public java.lang.String getInfoPersonaSchema(it.csi.rebus.rebuscrus.integration.iride.Ruolo in0) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.BadRuoloException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.getInfoPersonaSchema(in0);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Actor[] findActorsForPersonaInApplication(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.Application in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.findActorsForPersonaInApplication(in0, in1);
  }
  
  public it.csi.rebus.rebuscrus.integration.iride.Actor[] findActorsForPersonaInUseCase(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.UseCase in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.NoSuchUseCaseException, it.csi.rebus.rebuscrus.integration.iride.NoSuchApplicationException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.findActorsForPersonaInUseCase(in0, in1);
  }
  
  public boolean isPersonaInRuolo(it.csi.rebus.rebuscrus.integration.iride.Identita in0, it.csi.rebus.rebuscrus.integration.iride.Ruolo in1) throws java.rmi.RemoteException, it.csi.rebus.rebuscrus.integration.iride.SystemException, it.csi.rebus.rebuscrus.integration.iride.BadRuoloException, it.csi.rebus.rebuscrus.integration.iride.IdentitaNonAutenticaException, it.csi.rebus.rebuscrus.integration.iride.InternalException, it.csi.rebus.rebuscrus.integration.iride.UnrecoverableException{
    if (policyEnforcerBaseService == null)
      _initPolicyEnforcerBaseServiceProxy();
    return policyEnforcerBaseService.isPersonaInRuolo(in0, in1);
  }
  
  
}