package com.invaliddomain.myfirstproject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.invaliddomain.myfirstproject.data.DataSyncService;

public class DataSyncBinding extends Activity {


    /**
     * Example of binding and unbinding to the local service.
     * bind to, receiving an object through which it can communicate with the service.
     */
        // Don't attempt to unbind from the service unless the client has received some
        // information about the service's state.
        private boolean mShouldUnbind;

        // To invoke the bound service, first make sure that this value
        // is not null.
        private DataSyncService mBoundService;

        private ServiceConnection mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  Because we have bound to a explicit
                // service that we know is running in our own process, we can
                // cast its IBinder to a concrete class and directly access it.
                mBoundService = ((DataSyncService.DataSyncBinder)service).getService();
            }

            public void onServiceDisconnected(ComponentName className) {
                // This is called when the connection with the service has been
                // unexpectedly disconnected -- that is, its process crashed.
                // Because it is running in our same process, we should never
                // see this happen.
                mBoundService = null;
            }
        };

    /**
     * When a service is started, it has a lifecycle that's independent of the component that started it. The service can run in the background indefinitely, even if the component that started it is destroyed. As such, the service should stop itself when its job is complete by calling stopSelf(), or another component can stop it by calling stopService().
     * @return
     */
    Object doBindService() {
            return null;
        }

        void doUnbindService() {
            if (mShouldUnbind) {
                // Release information about the service's state.
                unbindService(mConnection);
                mShouldUnbind = false;
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            doUnbindService();
        }
}
