package com.htc.flashlight;

import android.util.Log;

public class Logger
{
  private static final String APTAG = "Flashlight";

  static void print(String paramString)
  {
    StackTraceElement localStackTraceElement = java.lang.Thread.currentThread().getStackTrace()[3];
    String str1 = localStackTraceElement.getClassName();
    String str2 = localStackTraceElement.getMethodName();
    int i = localStackTraceElement.getLineNumber();
    String str3 = str1 + "." + str2 + ":" + i + " " + paramString;
    int j = Log.d("Flashlight", str3);
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.Logger
 * JD-Core Version:    0.6.0
 */