package com.htc.flashlight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import java.util.Timer;
import java.util.TimerTask;

public class FlashlightBattery
{
  private static final int BATT_ANIMATION_INTERVAL = 1000;
  private static final int LOW_BATT_SHRESHOULD = 10;
  private static final int MSGID_BATT_ICON_HIDE = 5;
  private static final int MSGID_BATT_ICON_SHOW = 4;
  private boolean mBattIconVisiable = false;
  public BroadcastReceiver mBatteryInfoReceiver;
  private Handler mHandler = null;
  private Timer mTimer = null;

  public FlashlightBattery(Handler paramHandler)
  {
    1 local1 = new BroadcastReceiver()
    {
      public void onReceive(Context paramContext, Intent paramIntent)
      {
        String str = paramIntent.getAction();
        if (!"android.intent.action.BATTERY_CHANGED".equals(str))
          return;
        int i = paramIntent.getIntExtra("level", 1) * 100;
        int j = paramIntent.getIntExtra("scale", 1);
        int k = i / j;
        Logger.print("pct=" + k);
        if ((10 >= k) && (FlashlightControler.getInstance().isPowerOn()))
        {
          if (FlashlightBattery.this.mTimer != null)
          {
            FlashlightBattery.this.mTimer.cancel();
            Timer localTimer1 = FlashlightBattery.access$002(FlashlightBattery.this, null);
          }
          FlashlightBattery localFlashlightBattery = FlashlightBattery.this;
          Timer localTimer2 = new Timer();
          Timer localTimer3 = FlashlightBattery.access$002(localFlashlightBattery, localTimer2);
          Timer localTimer4 = FlashlightBattery.this.mTimer;
          1 local1 = new TimerTask()
          {
            public void run()
            {
              FlashlightBattery.this.refreshBattPaint();
            }
          };
          localTimer4.schedule(local1, 0L, 1000L);
          return;
        }
        if (FlashlightBattery.this.mTimer != null)
        {
          FlashlightBattery.this.mTimer.cancel();
          Timer localTimer5 = FlashlightBattery.access$002(FlashlightBattery.this, null);
        }
        Handler localHandler = FlashlightBattery.this.mHandler;
        Message localMessage = FlashlightBattery.this.mHandler.obtainMessage(5);
        if (localHandler.sendMessage(localMessage))
          return;
        Logger.print("BroadcastReceiver !sendMessage MSGID_BATT_ICON_HIDE");
      }
    };
    this.mBatteryInfoReceiver = local1;
    this.mHandler = paramHandler;
    Timer localTimer = new Timer();
    this.mTimer = localTimer;
  }

  public void refreshBattPaint()
  {
    if (this.mBattIconVisiable)
    {
      Handler localHandler1 = this.mHandler;
      Message localMessage1 = this.mHandler.obtainMessage(4);
      if (!localHandler1.sendMessage(localMessage1))
        Logger.print("refreshBattPaint !sendMessage MSGID_BATT_ICON_SHOW");
      if (this.mBattIconVisiable)
        break label83;
    }
    label83: for (boolean bool = true; ; bool = false)
    {
      this.mBattIconVisiable = bool;
      return;
      Handler localHandler2 = this.mHandler;
      Message localMessage2 = this.mHandler.obtainMessage(5);
      if (localHandler2.sendMessage(localMessage2))
        break;
      Logger.print("refreshBattPaint !sendMessage MSGID_BATT_ICON_HIDE");
      break;
    }
  }
}

/* Location:           /tmp/A/classes_dex2jar.jar
 * Qualified Name:     com.htc.flashlight.FlashlightBattery
 * JD-Core Version:    0.6.0
 */