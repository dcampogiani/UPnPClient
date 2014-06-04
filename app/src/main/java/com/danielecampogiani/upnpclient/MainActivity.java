package com.danielecampogiani.upnpclient;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.DeviceList;
import org.cybergarage.upnp.Service;
import org.cybergarage.upnp.ServiceList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    private PictureAdapter aa;
    private List<Picture> pictures;
    private ControlPoint controlPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictures = new ArrayList<Picture>();
        aa = new PictureAdapter(this,R.layout.image_adapter,pictures);
        setListAdapter(aa);
        controlPoint = new MyControlPoint();
        new SearchDevicesTask().execute();


    }




private class SearchDevicesTask extends AsyncTask<Void,Void,List<String>>{


    @Override
    protected List<String> doInBackground(Void... params) {
        Device myMac = null;

        controlPoint.start();

        while (myMac==null){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DeviceList rootDevices = controlPoint.getDeviceList();
            int numDevices = rootDevices.size();
            if (numDevices>0){
                for (int i =0;i<numDevices;i++){

                    if (rootDevices.getDevice(i).getFriendlyName().equals("Macbook Pro")) { //dovrei fare controllo con tipo non con nome
                        myMac = rootDevices.getDevice(i);
                        break;
                    }

                }
            }

        }// end while


        ServiceList services = myMac.getServiceList();

        for (int i =0;i<services.size();i++){
            Service service = services.getService(i);
            if(service.getServiceType().equals("urn:schemas-upnp-org:service:getimages:1")) {

                Action getImagesAction = service.getAction("GetImages");

                if (getImagesAction.postControlAction()){
                    Argument arg = getImagesAction.getArgument("ReturnText");
                    String value = arg.getValue();
                    String[] resultArray = value.split(";");
                    ArrayList<String> result = new ArrayList<String>();

                    String ip = myMac.getURLBase().substring(myMac.getURLBase().indexOf("http://")+7);
                    ip = ip.substring(0,ip.indexOf(":"));

                    result.add(ip);

                    for (String current: resultArray)
                        result.add(current);

                    return result;
                }

            }//fine service
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<String> strings) {


        String ip = strings.get(0);
        strings.remove(0);

        for (String current: strings){
            String currentUrl = "http://"+ip+":9000"+"/"+current;
            Picture picture = new Picture(currentUrl);
            pictures.add(picture);
        }

        aa.notifyDataSetChanged();

    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controlPoint.stop();
    }
}


