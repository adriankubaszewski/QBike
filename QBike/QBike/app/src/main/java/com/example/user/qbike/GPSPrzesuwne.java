package com.example.user.qbike;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;



public class GPSPrzesuwne extends AppCompatActivity {



    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    static MediaPlayer mpZaszybko;
    static MediaPlayer mpMeta;

    public void writeExternalStorage(View view)
    {
        final StatystkiFunkcje StatFun = new StatystkiFunkcje();

        Toast.makeText(getApplicationContext(), StatFun.zapiszTraseDoPliku(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsprzesuwne);

        mpZaszybko = MediaPlayer.create(this,R.raw.zwolnij);
        mpMeta = MediaPlayer.create(this,R.raw.meta);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpsprzesuwne, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//
//
//        public static Handler UIHandler;
//
//        static
//        {
//            UIHandler = new Handler(Looper.getMainLooper());
//        }
//
//        public static void runOnUI(Runnable runnable) {
//            UIHandler.post(runnable);
//        }
//
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
/////////////////////////////////////////////////////////////////////
//
//
////////////////////////////////////////////////////////////
//
//
//            final StatystkiFunkcje StatFun = new StatystkiFunkcje();
//
//            if(getArguments().getInt(ARG_SECTION_NUMBER)==1)
//            {
//
//
//            }
//            else if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
//            {
//                View rootView = inflater.inflate(R.layout.fragment_strona2, container, false);
//
//                final TextView textView1 = (TextView)rootView.findViewById(R.id.textView2View2);
//
//
//                final LocationManager managerLokalizacji = (LocationManager)
//                        getContext().getSystemService(Context.LOCATION_SERVICE);
//
//                listenerLokalizacji = new LocationListener() {
//
//                    @Override
//                    public void onLocationChanged(Location nowalokacja) {
//
//                        StatFun.ustawNowaLokacja(nowalokacja.getLatitude(),nowalokacja.getLongitude(),nowalokacja.getAltitude());
//
//                        String text = StatFun.wypiszWspolrzedne();
//
//                        //Lokalizacja.setText(text+"\n"+wysokosc);
//
//                        if(StatFun.odczytanepunkty>0)
//                        {
//                            // pomiar dystansu
//                            StatFun.odcinek = StatFun.poprzednialokacja.distanceTo(nowalokacja);
//
//                            StatFun.dystans += StatFun.odcinek;
//
//                            // wypisanie dystansu
//
//                            //TekstView2.setText("tekscik");
//
//                            StatFun.datapo= new Date();
//
//                            long czasruchu = (StatFun.datapo.getTime()-StatFun.dataprzed.getTime())/1000;
//                            StatFun.dataprzed = StatFun.datapo;
//
//
//                            //pomiar prędkości
//                            StatFun.obliczPredkosc();
//
//
//                            if(StatFun.predkosc > 20)
//                            {
//                                mpZaszybko.start();
//                            }
//
//                            if(StatFun.dystans > 1000)
//                            {
//                                mpMeta.start();
//                            }
//
//                            // czas trasy
//                            //Date czasodpoczatku = new Date(datapo.getTime() - czasstartu.getTime());
//                            StatFun.obliczCzasOdStartu();
//
//                            //srednia predkosc
//                            StatFun.obliczPredkoscSred();
//
//                            //predkosc max
//                            textView1.setText(StatFun.wypiszDystans() + "km");
//
//
//                        }
//                        if(StatFun.odczytanepunkty==0) {
//                            StatFun.dataprzed = new Date();
//                            StatFun.czasstartu = StatFun.dataprzed;
//                        }
//                        StatFun.poprzednialokacja.setLatitude(nowalokacja.getLatitude());
//                        StatFun.poprzednialokacja.setLongitude(nowalokacja.getLongitude());
//
//                        StatFun.odczytanepunkty++;
//
//                        //StatFun.listapozycji.add(text);
//
//
//                    }
//
//
//
//                    @Override
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                    }
//
//                    @Override
//                    public void onProviderEnabled(String provider) {
//                    }
//
//                    @Override
//                    public void onProviderDisabled(String provider) {
//                    }
//                };
//
//                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//                {
//                    //return;
//                }
//                managerLokalizacji.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, listenerLokalizacji);
//
//                //final TextView TekstView2 = (TextView) rootView.findViewById(R.id.textView2View2);
//
//                //TekstView2.setText("tekscik");
//
//                /*Runnable runnable = new Runnable(){
//
//                    @Override
//                    public void run()
//                    {
//                        while (true)
//                        {
//                            TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                            try {
//                                //usypiamy wątek na 100 milisekund
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                };*/
//
//
//                /*final Handler handler1 = new Handler();
//                Runnable runnable = new Runnable() {
//                    private long startTime = System.currentTimeMillis();
//                    public void run() {
//                        while (true) {
//                            try {
//                                Thread.sleep(1000);
//                            }
//                            catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            handler1.post(new Runnable(){
//                                public void run() {
//                                    TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                                }
//                            });
//                        }
//                    }
//                };
//                new Thread(runnable).start();*/
//
//
//                /*int i = 0;
//                new Thread(new Runnable() {
//                    public void run() {
//                        while(true) {
//                            TekstView2.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                                }
//                            });
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//
//                            }
//                        }
//
//                    }
//                }).start();*/
//
//                /*new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch( InterruptedException e ) {
//
//                        }
//
//                        runOnUI(new Runnable(){
//                            @Override
//                            public void run() {
//                                TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                            }
//                        });
//                    }
//                }).start();*/
//
//                /*Runnable runnable = new Runnable() {
//                    public void run() {
//
//                        long endTime = System.currentTimeMillis() + 20*1000;
//
//                        while (System.currentTimeMillis() < endTime) {
//                            synchronized (this) {
//                                try {
//                                    wait(endTime - System.currentTimeMillis());
//                                } catch (Exception e) {}
//                            }
//                        }
//                    }
//                };
//                Thread mythread = new Thread(runnable);
//                mythread.start();*/
//
//                //TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                //TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                /*int i = 0;
//                while(i%1000==0)
//                {
//                    TekstView2.setText("tekt"+i);
//                }*/
//
//
//                //TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//
//                //new Thread(new Runnable() {
//                //public void run() {
//                        /*Timer timer = new Timer();
//                        timer.scheduleAtFixedRate(new TimerTask()
//                        {
//                            public void run()
//                            {
//                                TekstView2.setText(StatFun.wypiszPredkosc() + "km/h");
//                            }
//                        }, 8000, 1000);*/
//                // }
//                // }).start();
//
//
//
//                return rootView;
//            }
//            else
//            {
//                View rootView = inflater.inflate(R.layout.fragment_strona3, container, false);
//                //View rootView = inflater.inflate(R.layout.fragment_gpsprzesuwne, container, false);
//
//                TextView textView = (TextView) rootView.findViewById(R.id.textView2strona3);
//
//                textView.setText(StatFun.wypiszPredkosc() + "km/h");
//
//                //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//                return rootView;
//            }
//        }
//
//
//
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position){
                case 0:
                    Strona1 str1 = new Strona1();
                    return str1;
                case 1:
                    Strona2 str2 = new Strona2();
                    return str2;
                case 2:
                    Strona3 str3 = new Strona3();
                    return str3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PARAMETRY";
                case 1:
                    return "MAPA";
                case 2:
                    return "PROGNOZA";
            }
            return null;
        }
    }
}

