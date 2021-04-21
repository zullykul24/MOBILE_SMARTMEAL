/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkPublicKey {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CkPublicKey(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkPublicKey obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkPublicKey(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CkPublicKey() {
    this(chilkatJNI.new_CkPublicKey(), true);
  }

  public void LastErrorXml(CkString str) {
    chilkatJNI.CkPublicKey_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorHtml(CkString str) {
    chilkatJNI.CkPublicKey_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorText(CkString str) {
    chilkatJNI.CkPublicKey_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void get_DebugLogFilePath(CkString str) {
    chilkatJNI.CkPublicKey_get_DebugLogFilePath(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String debugLogFilePath() {
    return chilkatJNI.CkPublicKey_debugLogFilePath(swigCPtr, this);
  }

  public void put_DebugLogFilePath(String newVal) {
    chilkatJNI.CkPublicKey_put_DebugLogFilePath(swigCPtr, this, newVal);
  }

  public int get_KeySize() {
    return chilkatJNI.CkPublicKey_get_KeySize(swigCPtr, this);
  }

  public void get_KeyType(CkString str) {
    chilkatJNI.CkPublicKey_get_KeyType(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String keyType() {
    return chilkatJNI.CkPublicKey_keyType(swigCPtr, this);
  }

  public void get_LastErrorHtml(CkString str) {
    chilkatJNI.CkPublicKey_get_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorHtml() {
    return chilkatJNI.CkPublicKey_lastErrorHtml(swigCPtr, this);
  }

  public void get_LastErrorText(CkString str) {
    chilkatJNI.CkPublicKey_get_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorText() {
    return chilkatJNI.CkPublicKey_lastErrorText(swigCPtr, this);
  }

  public void get_LastErrorXml(CkString str) {
    chilkatJNI.CkPublicKey_get_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorXml() {
    return chilkatJNI.CkPublicKey_lastErrorXml(swigCPtr, this);
  }

  public boolean get_LastMethodSuccess() {
    return chilkatJNI.CkPublicKey_get_LastMethodSuccess(swigCPtr, this);
  }

  public void put_LastMethodSuccess(boolean newVal) {
    chilkatJNI.CkPublicKey_put_LastMethodSuccess(swigCPtr, this, newVal);
  }

  public boolean get_VerboseLogging() {
    return chilkatJNI.CkPublicKey_get_VerboseLogging(swigCPtr, this);
  }

  public void put_VerboseLogging(boolean newVal) {
    chilkatJNI.CkPublicKey_put_VerboseLogging(swigCPtr, this, newVal);
  }

  public void get_Version(CkString str) {
    chilkatJNI.CkPublicKey_get_Version(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String version() {
    return chilkatJNI.CkPublicKey_version(swigCPtr, this);
  }

  public boolean GetDer(boolean preferPkcs1, CkByteData outBytes) {
    return chilkatJNI.CkPublicKey_GetDer(swigCPtr, this, preferPkcs1, CkByteData.getCPtr(outBytes), outBytes);
  }

  public boolean GetEncoded(boolean preferPkcs1, String encoding, CkString outStr) {
    return chilkatJNI.CkPublicKey_GetEncoded(swigCPtr, this, preferPkcs1, encoding, CkString.getCPtr(outStr), outStr);
  }

  public String getEncoded(boolean preferPkcs1, String encoding) {
    return chilkatJNI.CkPublicKey_getEncoded(swigCPtr, this, preferPkcs1, encoding);
  }

  public String encoded(boolean preferPkcs1, String encoding) {
    return chilkatJNI.CkPublicKey_encoded(swigCPtr, this, preferPkcs1, encoding);
  }

  public boolean GetJwk(CkString outStr) {
    return chilkatJNI.CkPublicKey_GetJwk(swigCPtr, this, CkString.getCPtr(outStr), outStr);
  }

  public String getJwk() {
    return chilkatJNI.CkPublicKey_getJwk(swigCPtr, this);
  }

  public String jwk() {
    return chilkatJNI.CkPublicKey_jwk(swigCPtr, this);
  }

  public boolean GetJwkThumbprint(String hashAlg, CkString outStr) {
    return chilkatJNI.CkPublicKey_GetJwkThumbprint(swigCPtr, this, hashAlg, CkString.getCPtr(outStr), outStr);
  }

  public String getJwkThumbprint(String hashAlg) {
    return chilkatJNI.CkPublicKey_getJwkThumbprint(swigCPtr, this, hashAlg);
  }

  public String jwkThumbprint(String hashAlg) {
    return chilkatJNI.CkPublicKey_jwkThumbprint(swigCPtr, this, hashAlg);
  }

  public boolean GetOpenSslDer(CkByteData outData) {
    return chilkatJNI.CkPublicKey_GetOpenSslDer(swigCPtr, this, CkByteData.getCPtr(outData), outData);
  }

  public boolean GetOpenSslPem(CkString outStr) {
    return chilkatJNI.CkPublicKey_GetOpenSslPem(swigCPtr, this, CkString.getCPtr(outStr), outStr);
  }

  public String getOpenSslPem() {
    return chilkatJNI.CkPublicKey_getOpenSslPem(swigCPtr, this);
  }

  public String openSslPem() {
    return chilkatJNI.CkPublicKey_openSslPem(swigCPtr, this);
  }

  public boolean GetPem(boolean preferPkcs1, CkString outStr) {
    return chilkatJNI.CkPublicKey_GetPem(swigCPtr, this, preferPkcs1, CkString.getCPtr(outStr), outStr);
  }

  public String getPem(boolean preferPkcs1) {
    return chilkatJNI.CkPublicKey_getPem(swigCPtr, this, preferPkcs1);
  }

  public String pem(boolean preferPkcs1) {
    return chilkatJNI.CkPublicKey_pem(swigCPtr, this, preferPkcs1);
  }

  public boolean GetPkcs1ENC(String encoding, CkString outStr) {
    return chilkatJNI.CkPublicKey_GetPkcs1ENC(swigCPtr, this, encoding, CkString.getCPtr(outStr), outStr);
  }

  public String getPkcs1ENC(String encoding) {
    return chilkatJNI.CkPublicKey_getPkcs1ENC(swigCPtr, this, encoding);
  }

  public String pkcs1ENC(String encoding) {
    return chilkatJNI.CkPublicKey_pkcs1ENC(swigCPtr, this, encoding);
  }

  public boolean GetPkcs8ENC(String encoding, CkString outStr) {
    return chilkatJNI.CkPublicKey_GetPkcs8ENC(swigCPtr, this, encoding, CkString.getCPtr(outStr), outStr);
  }

  public String getPkcs8ENC(String encoding) {
    return chilkatJNI.CkPublicKey_getPkcs8ENC(swigCPtr, this, encoding);
  }

  public String pkcs8ENC(String encoding) {
    return chilkatJNI.CkPublicKey_pkcs8ENC(swigCPtr, this, encoding);
  }

  public boolean GetRsaDer(CkByteData outData) {
    return chilkatJNI.CkPublicKey_GetRsaDer(swigCPtr, this, CkByteData.getCPtr(outData), outData);
  }

  public boolean GetXml(CkString outStr) {
    return chilkatJNI.CkPublicKey_GetXml(swigCPtr, this, CkString.getCPtr(outStr), outStr);
  }

  public String getXml() {
    return chilkatJNI.CkPublicKey_getXml(swigCPtr, this);
  }

  public String xml() {
    return chilkatJNI.CkPublicKey_xml(swigCPtr, this);
  }

  public boolean LoadBase64(String keyStr) {
    return chilkatJNI.CkPublicKey_LoadBase64(swigCPtr, this, keyStr);
  }

  public boolean LoadBd(CkBinData bd) {
    return chilkatJNI.CkPublicKey_LoadBd(swigCPtr, this, CkBinData.getCPtr(bd), bd);
  }

  public boolean LoadEcdsa(String curveName, String Qx, String Qy) {
    return chilkatJNI.CkPublicKey_LoadEcdsa(swigCPtr, this, curveName, Qx, Qy);
  }

  public boolean LoadEd25519(String pubKey) {
    return chilkatJNI.CkPublicKey_LoadEd25519(swigCPtr, this, pubKey);
  }

  public boolean LoadFromBinary(CkByteData keyBytes) {
    return chilkatJNI.CkPublicKey_LoadFromBinary(swigCPtr, this, CkByteData.getCPtr(keyBytes), keyBytes);
  }

  public boolean LoadFromFile(String path) {
    return chilkatJNI.CkPublicKey_LoadFromFile(swigCPtr, this, path);
  }

  public boolean LoadFromString(String keyString) {
    return chilkatJNI.CkPublicKey_LoadFromString(swigCPtr, this, keyString);
  }

  public boolean LoadOpenSslDer(CkByteData data) {
    return chilkatJNI.CkPublicKey_LoadOpenSslDer(swigCPtr, this, CkByteData.getCPtr(data), data);
  }

  public boolean LoadOpenSslDerFile(String path) {
    return chilkatJNI.CkPublicKey_LoadOpenSslDerFile(swigCPtr, this, path);
  }

  public boolean LoadOpenSslPem(String str) {
    return chilkatJNI.CkPublicKey_LoadOpenSslPem(swigCPtr, this, str);
  }

  public boolean LoadOpenSslPemFile(String path) {
    return chilkatJNI.CkPublicKey_LoadOpenSslPemFile(swigCPtr, this, path);
  }

  public boolean LoadPkcs1Pem(String str) {
    return chilkatJNI.CkPublicKey_LoadPkcs1Pem(swigCPtr, this, str);
  }

  public boolean LoadRsaDer(CkByteData data) {
    return chilkatJNI.CkPublicKey_LoadRsaDer(swigCPtr, this, CkByteData.getCPtr(data), data);
  }

  public boolean LoadRsaDerFile(String path) {
    return chilkatJNI.CkPublicKey_LoadRsaDerFile(swigCPtr, this, path);
  }

  public boolean LoadXml(String xml) {
    return chilkatJNI.CkPublicKey_LoadXml(swigCPtr, this, xml);
  }

  public boolean LoadXmlFile(String path) {
    return chilkatJNI.CkPublicKey_LoadXmlFile(swigCPtr, this, path);
  }

  public boolean SaveDerFile(boolean preferPkcs1, String path) {
    return chilkatJNI.CkPublicKey_SaveDerFile(swigCPtr, this, preferPkcs1, path);
  }

  public boolean SaveLastError(String path) {
    return chilkatJNI.CkPublicKey_SaveLastError(swigCPtr, this, path);
  }

  public boolean SaveOpenSslDerFile(String path) {
    return chilkatJNI.CkPublicKey_SaveOpenSslDerFile(swigCPtr, this, path);
  }

  public boolean SaveOpenSslPemFile(String path) {
    return chilkatJNI.CkPublicKey_SaveOpenSslPemFile(swigCPtr, this, path);
  }

  public boolean SavePemFile(boolean preferPkcs1, String path) {
    return chilkatJNI.CkPublicKey_SavePemFile(swigCPtr, this, preferPkcs1, path);
  }

  public boolean SaveRsaDerFile(String path) {
    return chilkatJNI.CkPublicKey_SaveRsaDerFile(swigCPtr, this, path);
  }

  public boolean SaveXmlFile(String path) {
    return chilkatJNI.CkPublicKey_SaveXmlFile(swigCPtr, this, path);
  }

}
