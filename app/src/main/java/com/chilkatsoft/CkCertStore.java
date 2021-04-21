/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkCertStore {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CkCertStore(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkCertStore obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkCertStore(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CkCertStore() {
    this(chilkatJNI.new_CkCertStore(), true);
  }

  public void LastErrorXml(CkString str) {
    chilkatJNI.CkCertStore_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorHtml(CkString str) {
    chilkatJNI.CkCertStore_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorText(CkString str) {
    chilkatJNI.CkCertStore_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public boolean get_AvoidWindowsPkAccess() {
    return chilkatJNI.CkCertStore_get_AvoidWindowsPkAccess(swigCPtr, this);
  }

  public void put_AvoidWindowsPkAccess(boolean newVal) {
    chilkatJNI.CkCertStore_put_AvoidWindowsPkAccess(swigCPtr, this, newVal);
  }

  public void get_DebugLogFilePath(CkString str) {
    chilkatJNI.CkCertStore_get_DebugLogFilePath(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String debugLogFilePath() {
    return chilkatJNI.CkCertStore_debugLogFilePath(swigCPtr, this);
  }

  public void put_DebugLogFilePath(String newVal) {
    chilkatJNI.CkCertStore_put_DebugLogFilePath(swigCPtr, this, newVal);
  }

  public void get_LastErrorHtml(CkString str) {
    chilkatJNI.CkCertStore_get_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorHtml() {
    return chilkatJNI.CkCertStore_lastErrorHtml(swigCPtr, this);
  }

  public void get_LastErrorText(CkString str) {
    chilkatJNI.CkCertStore_get_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorText() {
    return chilkatJNI.CkCertStore_lastErrorText(swigCPtr, this);
  }

  public void get_LastErrorXml(CkString str) {
    chilkatJNI.CkCertStore_get_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorXml() {
    return chilkatJNI.CkCertStore_lastErrorXml(swigCPtr, this);
  }

  public boolean get_LastMethodSuccess() {
    return chilkatJNI.CkCertStore_get_LastMethodSuccess(swigCPtr, this);
  }

  public void put_LastMethodSuccess(boolean newVal) {
    chilkatJNI.CkCertStore_put_LastMethodSuccess(swigCPtr, this, newVal);
  }

  public int get_NumCertificates() {
    return chilkatJNI.CkCertStore_get_NumCertificates(swigCPtr, this);
  }

  public void get_SmartCardPin(CkString str) {
    chilkatJNI.CkCertStore_get_SmartCardPin(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String smartCardPin() {
    return chilkatJNI.CkCertStore_smartCardPin(swigCPtr, this);
  }

  public void put_SmartCardPin(String newVal) {
    chilkatJNI.CkCertStore_put_SmartCardPin(swigCPtr, this, newVal);
  }

  public boolean get_VerboseLogging() {
    return chilkatJNI.CkCertStore_get_VerboseLogging(swigCPtr, this);
  }

  public void put_VerboseLogging(boolean newVal) {
    chilkatJNI.CkCertStore_put_VerboseLogging(swigCPtr, this, newVal);
  }

  public void get_Version(CkString str) {
    chilkatJNI.CkCertStore_get_Version(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String version() {
    return chilkatJNI.CkCertStore_version(swigCPtr, this);
  }

  public CkCert FindCertByKeyContainer(String name) {
    long cPtr = chilkatJNI.CkCertStore_FindCertByKeyContainer(swigCPtr, this, name);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertByRfc822Name(String name) {
    long cPtr = chilkatJNI.CkCertStore_FindCertByRfc822Name(swigCPtr, this, name);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySerial(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySerial(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySha1Thumbprint(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySha1Thumbprint(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySubject(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySubject(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySubjectCN(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySubjectCN(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySubjectE(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySubjectE(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertBySubjectO(String str) {
    long cPtr = chilkatJNI.CkCertStore_FindCertBySubjectO(swigCPtr, this, str);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert FindCertForEmail(String emailAddress) {
    long cPtr = chilkatJNI.CkCertStore_FindCertForEmail(swigCPtr, this, emailAddress);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public CkCert GetCertificate(int index) {
    long cPtr = chilkatJNI.CkCertStore_GetCertificate(swigCPtr, this, index);
    return (cPtr == 0) ? null : new CkCert(cPtr, true);
  }

  public boolean LoadPemFile(String pemPath) {
    return chilkatJNI.CkCertStore_LoadPemFile(swigCPtr, this, pemPath);
  }

  public boolean LoadPemStr(String pemString) {
    return chilkatJNI.CkCertStore_LoadPemStr(swigCPtr, this, pemString);
  }

  public boolean LoadPfxData(CkByteData pfxData, String password) {
    return chilkatJNI.CkCertStore_LoadPfxData(swigCPtr, this, CkByteData.getCPtr(pfxData), pfxData, password);
  }

  public boolean LoadPfxFile(String pfxFilename, String password) {
    return chilkatJNI.CkCertStore_LoadPfxFile(swigCPtr, this, pfxFilename, password);
  }

  public boolean SaveLastError(String path) {
    return chilkatJNI.CkCertStore_SaveLastError(swigCPtr, this, path);
  }

}
