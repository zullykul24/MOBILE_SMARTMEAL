/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.chilkatsoft;

public class CkRss {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected CkRss(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(CkRss obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        chilkatJNI.delete_CkRss(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public CkRss() {
    this(chilkatJNI.new_CkRss(), true);
  }

  public void LastErrorXml(CkString str) {
    chilkatJNI.CkRss_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorHtml(CkString str) {
    chilkatJNI.CkRss_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void LastErrorText(CkString str) {
    chilkatJNI.CkRss_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public void put_EventCallbackObject(CkBaseProgress progress) {
    chilkatJNI.CkRss_put_EventCallbackObject(swigCPtr, this, CkBaseProgress.getCPtr(progress), progress);
  }

  public void get_DebugLogFilePath(CkString str) {
    chilkatJNI.CkRss_get_DebugLogFilePath(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String debugLogFilePath() {
    return chilkatJNI.CkRss_debugLogFilePath(swigCPtr, this);
  }

  public void put_DebugLogFilePath(String newVal) {
    chilkatJNI.CkRss_put_DebugLogFilePath(swigCPtr, this, newVal);
  }

  public void get_LastErrorHtml(CkString str) {
    chilkatJNI.CkRss_get_LastErrorHtml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorHtml() {
    return chilkatJNI.CkRss_lastErrorHtml(swigCPtr, this);
  }

  public void get_LastErrorText(CkString str) {
    chilkatJNI.CkRss_get_LastErrorText(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorText() {
    return chilkatJNI.CkRss_lastErrorText(swigCPtr, this);
  }

  public void get_LastErrorXml(CkString str) {
    chilkatJNI.CkRss_get_LastErrorXml(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String lastErrorXml() {
    return chilkatJNI.CkRss_lastErrorXml(swigCPtr, this);
  }

  public boolean get_LastMethodSuccess() {
    return chilkatJNI.CkRss_get_LastMethodSuccess(swigCPtr, this);
  }

  public void put_LastMethodSuccess(boolean newVal) {
    chilkatJNI.CkRss_put_LastMethodSuccess(swigCPtr, this, newVal);
  }

  public int get_NumChannels() {
    return chilkatJNI.CkRss_get_NumChannels(swigCPtr, this);
  }

  public int get_NumItems() {
    return chilkatJNI.CkRss_get_NumItems(swigCPtr, this);
  }

  public boolean get_VerboseLogging() {
    return chilkatJNI.CkRss_get_VerboseLogging(swigCPtr, this);
  }

  public void put_VerboseLogging(boolean newVal) {
    chilkatJNI.CkRss_put_VerboseLogging(swigCPtr, this, newVal);
  }

  public void get_Version(CkString str) {
    chilkatJNI.CkRss_get_Version(swigCPtr, this, CkString.getCPtr(str), str);
  }

  public String version() {
    return chilkatJNI.CkRss_version(swigCPtr, this);
  }

  public CkRss AddNewChannel() {
    long cPtr = chilkatJNI.CkRss_AddNewChannel(swigCPtr, this);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public CkRss AddNewImage() {
    long cPtr = chilkatJNI.CkRss_AddNewImage(swigCPtr, this);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public CkRss AddNewItem() {
    long cPtr = chilkatJNI.CkRss_AddNewItem(swigCPtr, this);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public boolean DownloadRss(String url) {
    return chilkatJNI.CkRss_DownloadRss(swigCPtr, this, url);
  }

  public CkTask DownloadRssAsync(String url) {
    long cPtr = chilkatJNI.CkRss_DownloadRssAsync(swigCPtr, this, url);
    return (cPtr == 0) ? null : new CkTask(cPtr, true);
  }

  public boolean GetAttr(String tag, String attrName, CkString outStr) {
    return chilkatJNI.CkRss_GetAttr(swigCPtr, this, tag, attrName, CkString.getCPtr(outStr), outStr);
  }

  public String getAttr(String tag, String attrName) {
    return chilkatJNI.CkRss_getAttr(swigCPtr, this, tag, attrName);
  }

  public String attr(String tag, String attrName) {
    return chilkatJNI.CkRss_attr(swigCPtr, this, tag, attrName);
  }

  public CkRss GetChannel(int index) {
    long cPtr = chilkatJNI.CkRss_GetChannel(swigCPtr, this, index);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public int GetCount(String tag) {
    return chilkatJNI.CkRss_GetCount(swigCPtr, this, tag);
  }

  public boolean GetDate(String tag, SYSTEMTIME outSysTime) {
    return chilkatJNI.CkRss_GetDate(swigCPtr, this, tag, SYSTEMTIME.getCPtr(outSysTime), outSysTime);
  }

  public boolean GetDateStr(String tag, CkString outStr) {
    return chilkatJNI.CkRss_GetDateStr(swigCPtr, this, tag, CkString.getCPtr(outStr), outStr);
  }

  public String getDateStr(String tag) {
    return chilkatJNI.CkRss_getDateStr(swigCPtr, this, tag);
  }

  public String dateStr(String tag) {
    return chilkatJNI.CkRss_dateStr(swigCPtr, this, tag);
  }

  public CkRss GetImage() {
    long cPtr = chilkatJNI.CkRss_GetImage(swigCPtr, this);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public int GetInt(String tag) {
    return chilkatJNI.CkRss_GetInt(swigCPtr, this, tag);
  }

  public CkRss GetItem(int index) {
    long cPtr = chilkatJNI.CkRss_GetItem(swigCPtr, this, index);
    return (cPtr == 0) ? null : new CkRss(cPtr, true);
  }

  public boolean GetString(String tag, CkString outStr) {
    return chilkatJNI.CkRss_GetString(swigCPtr, this, tag, CkString.getCPtr(outStr), outStr);
  }

  public String getString(String tag) {
    return chilkatJNI.CkRss_getString(swigCPtr, this, tag);
  }

  public String string(String tag) {
    return chilkatJNI.CkRss_string(swigCPtr, this, tag);
  }

  public boolean LoadRssFile(String filePath) {
    return chilkatJNI.CkRss_LoadRssFile(swigCPtr, this, filePath);
  }

  public boolean LoadRssString(String rssString) {
    return chilkatJNI.CkRss_LoadRssString(swigCPtr, this, rssString);
  }

  public boolean LoadTaskCaller(CkTask task) {
    return chilkatJNI.CkRss_LoadTaskCaller(swigCPtr, this, CkTask.getCPtr(task), task);
  }

  public boolean MGetAttr(String tag, int index, String attrName, CkString outStr) {
    return chilkatJNI.CkRss_MGetAttr(swigCPtr, this, tag, index, attrName, CkString.getCPtr(outStr), outStr);
  }

  public String mGetAttr(String tag, int index, String attrName) {
    return chilkatJNI.CkRss_mGetAttr(swigCPtr, this, tag, index, attrName);
  }

  public boolean MGetString(String tag, int index, CkString outStr) {
    return chilkatJNI.CkRss_MGetString(swigCPtr, this, tag, index, CkString.getCPtr(outStr), outStr);
  }

  public String mGetString(String tag, int index) {
    return chilkatJNI.CkRss_mGetString(swigCPtr, this, tag, index);
  }

  public boolean MSetAttr(String tag, int idx, String attrName, String value) {
    return chilkatJNI.CkRss_MSetAttr(swigCPtr, this, tag, idx, attrName, value);
  }

  public boolean MSetString(String tag, int idx, String value) {
    return chilkatJNI.CkRss_MSetString(swigCPtr, this, tag, idx, value);
  }

  public void NewRss() {
    chilkatJNI.CkRss_NewRss(swigCPtr, this);
  }

  public void Remove(String tag) {
    chilkatJNI.CkRss_Remove(swigCPtr, this, tag);
  }

  public boolean SaveLastError(String path) {
    return chilkatJNI.CkRss_SaveLastError(swigCPtr, this, path);
  }

  public void SetAttr(String tag, String attrName, String value) {
    chilkatJNI.CkRss_SetAttr(swigCPtr, this, tag, attrName, value);
  }

  public void SetDate(String tag, SYSTEMTIME dateTime) {
    chilkatJNI.CkRss_SetDate(swigCPtr, this, tag, SYSTEMTIME.getCPtr(dateTime), dateTime);
  }

  public void SetDateNow(String tag) {
    chilkatJNI.CkRss_SetDateNow(swigCPtr, this, tag);
  }

  public void SetDateStr(String tag, String dateTimeStr) {
    chilkatJNI.CkRss_SetDateStr(swigCPtr, this, tag, dateTimeStr);
  }

  public void SetInt(String tag, int value) {
    chilkatJNI.CkRss_SetInt(swigCPtr, this, tag, value);
  }

  public void SetString(String tag, String value) {
    chilkatJNI.CkRss_SetString(swigCPtr, this, tag, value);
  }

  public boolean ToXmlString(CkString outStr) {
    return chilkatJNI.CkRss_ToXmlString(swigCPtr, this, CkString.getCPtr(outStr), outStr);
  }

  public String toXmlString() {
    return chilkatJNI.CkRss_toXmlString(swigCPtr, this);
  }

}
