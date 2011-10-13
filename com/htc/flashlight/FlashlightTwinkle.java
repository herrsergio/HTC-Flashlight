package com.htc.flashlight;

import android.os.Handler;
import android.os.Message;
import java.util.Timer;
import java.util.TimerTask;

public class FlashlightTwinkle
{
  private static final int AUTO_FLASH_INTERVAL = 500;
  private static final int MSGID_FLASH_OFF = 7;
  private static final int MSGID_FLASH_ON = 6;
  private static final int SOS_1ST_SHORT_END = 3;
  private static final int SOS_1ST_SHORT_END_INTERVAL = 1500;
  private static final int SOS_2ND_SHORT_END = 6;
  private static final int SOS_INTERVAL = 500;
  private static final int SOS_LONG_END = 3;
  private static final int SOS_LONG_END_INTERVAL = 1500;
  private static final int SOS_LONG_INTERVAL = 1500;
  private static final int SOS_PAUSE_INTERVAL = 3500;
  public static final int STATE_SOS_LONG = 2;
  public static final int STATE_SOS_OFF = 0;
  public static final int STATE_SOS_PAUSE = 3;
  public static final int STATE_SOS_SHORT = 1;
  private boolean mAutoFlash;
  private boolean mAutoFlashOn = false;
  private int mCurrentSOSState = 0;
  private Handler mHandler = null;
  private int mLongSOSState_Count = 0;
  private boolean mSOS;
  private int mShortSOSState_Count = 0;
  private Timer mTimer = null;

  public FlashlightTwinkle(Handler paramHandler)
  {
    this.mHandler = paramHandler;
    Timer localTimer = new Timer();
    this.mTimer = localTimer;
  }

  private void ToAutoFlashSwitch()
  {
    if (this.mAutoFlashOn)
    {
      Handler localHandler1 = this.mHandler;
      Message localMessage1 = this.mHandler.obtainMessage(6);
      if (!localHandler1.sendMessage(localMessage1))
        Logger.print("ToAutoFlashSwitch !sendMessage MSGID_FLASH_ON");
      if (this.mAutoFlashOn)
        break label85;
    }
    label85: for (boolean bool = true; ; bool = false)
    {
      this.mAutoFlashOn = bool;
      return;
      Handler localHandler2 = this.mHandler;
      Message localMessage2 = this.mHandler.obtainMessage(7);
      if (localHandler2.sendMessage(localMessage2))
        break;
      Logger.print("ToAutoFlashSwitch !sendMessage MSGID_FLASH_OFF");
      break;
    }
  }

  private void ToFlashSOS()
  {
    long l;
    if ((this.mAutoFlashOn) && (this.mCurrentSOSState != 3))
    {
      Handler localHandler1 = this.mHandler;
      Message localMessage1 = this.mHandler.obtainMessage(6);
      if (!localHandler1.sendMessage(localMessage1))
        Logger.print("ToFlashSOS() !sendMessage MSGID_FLASH_OFF");
      if (this.mCurrentSOSState == 2)
        l = 1500L;
    }
    while (true)
    {
      try
      {
        Thread.sleep(l);
        if (this.mAutoFlashOn)
          break label345;
        bool = true;
        this.mAutoFlashOn = bool;
        return;
      }
      catch (InterruptedException localInterruptedException1)
      {
        localInterruptedException1.printStackTrace();
        continue;
      }
      StringBuilder localStringBuilder = new StringBuilder().append("ToFlashSOS() 0-0 mCurrentSOSState=");
      int i = this.mCurrentSOSState;
      Logger.print(i);
      if (this.mCurrentSOSState == 0)
      {
        this.mCurrentSOSState = 1;
        label127: Handler localHandler2 = this.mHandler;
        Message localMessage2 = this.mHandler.obtainMessage(7);
        if (!localHandler2.sendMessage(localMessage2))
          Logger.print("ToAutoFlashSwitch !sendMessage MSGID_FLASH_OFF");
        if (this.mCurrentSOSState != 3);
      }
      try
      {
        Logger.print("ToFlashSOS() 0-0 Sleep PAUSE");
        Thread.sleep(3500L);
        if ((this.mShortSOSState_Count == 3) && (this.mCurrentSOSState == 1))
          l = 1500L;
      }
      catch (InterruptedException localInterruptedException2)
      {
        try
        {
          Thread.sleep(l);
          this.mCurrentSOSState = 2;
          continue;
          if (this.mCurrentSOSState == 1)
          {
            int j = this.mShortSOSState_Count + 1;
            this.mShortSOSState_Count = j;
            break label127;
          }
          if (this.mCurrentSOSState != 2)
            break label127;
          int k = this.mLongSOSState_Count + 1;
          this.mLongSOSState_Count = k;
          break label127;
          localInterruptedException2.printStackTrace();
        }
        catch (InterruptedException localInterruptedException3)
        {
          while (true)
            localInterruptedException3.printStackTrace();
        }
        if (this.mLongSOSState_Count == 3)
        {
          l = 1500L;
          try
          {
            Thread.sleep(l);
            this.mLongSOSState_Count = 0;
            this.mCurrentSOSState = 1;
          }
          catch (InterruptedException localInterruptedException4)
          {
            while (true)
              localInterruptedException4.printStackTrace();
          }
        }
        if (this.mShortSOSState_Count == 6)
        {
          this.mShortSOSState_Count = 0;
          this.mCurrentSOSState = 3;
        }
      }
      if (this.mCurrentSOSState != 3)
        continue;
      this.mCurrentSOSState = 0;
      continue;
      label345: boolean bool = false;
    }
  }

  public void FlashSOS(boolean paramBoolean)
  {
    Logger.print("FlashSOS IsOn=" + paramBoolean);
    this.mSOS = paramBoolean;
    if (paramBoolean)
    {
      if (this.mTimer != null)
      {
        this.mTimer.cancel();
        this.mTimer = null;
      }
      this.mCurrentSOSState = 0;
      this.mShortSOSState_Count = 0;
      this.mLongSOSState_Count = 0;
      Timer localTimer1 = new Timer();
      this.mTimer = localTimer1;
      Timer localTimer2 = this.mTimer;
      2 local2 = new TimerTask()
      {
        public void run()
        {
          FlashlightTwinkle.this.ToFlashSOS();
        }
      };
      localTimer2.schedule(local2, 0L, 500L);
      return;
    }
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    Handler localHandler = this.mHandler;
    Message localMessage = this.mHandler.obtainMessage(7);
    if (localHandler.sendMessage(localMessage))
      return;
    Logger.print("FlashSOS !sendMessage MSGID_SOS_OFF");
  }

  public void autoFlashSwitch(boolean paramBoolean)
  {
    this.mAutoFlash = paramBoolean;
    if (paramBoolean)
    {
      if (this.mTimer != null)
      {
        this.mTimer.cancel();
        this.mTimer = null;
      }
      Timer localTimer1 = new Timer();
      this.mTimer = localTimer1;
      Timer localTimer2 = this.mTimer;
      1 local1 = new TimerTask()
      {
        public void run()
        {
          FlashlightTwinkle.this.ToAutoFlashSwitch();
        }
      };
      localTimer2.schedule(local1, 0L, 500L);
      return;
    }
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    Handler localHandler = this.mHandler;
    Message localMessage = this.mHandler.obtainMessage(7);
    if (localHandler.sendMessage(localMessage))
      return;
    Logger.print("autoFlashSwitch !sendMessage MSGID_FLASH_OFF");
  }

  public boolean isAutoFlash()
  {
    return this.mAutoFlash;
  }

  public boolean isSOS()
  {
    return this.mSOS;
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.FlashlightTwinkle
 * JD-Core Version:    0.6.0
 */