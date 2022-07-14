/*
* SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/
/**
 * PolicyEnforcerBaseServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Nov 13, 2013 (09:13:21 GMT) WSDL2Java emitter.
 */

package it.csi.rebus.anagraficasrv.integration.iride;

public interface PolicyEnforcerBaseServiceService extends javax.xml.rpc.Service {
    public java.lang.String getPolicyEnforcerBaseAddress();

    public it.csi.rebus.anagraficasrv.integration.iride.PolicyEnforcerBaseService getPolicyEnforcerBase() throws javax.xml.rpc.ServiceException;

    public it.csi.rebus.anagraficasrv.integration.iride.PolicyEnforcerBaseService getPolicyEnforcerBase(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
