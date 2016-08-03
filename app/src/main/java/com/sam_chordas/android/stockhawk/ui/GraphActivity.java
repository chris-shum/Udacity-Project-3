package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.sam_chordas.android.stockhawk.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class GraphActivity extends Activity {
    ArrayList<String> priceArray2;
    ArrayList<Entry> priceArray;
    ArrayList<String> dateArray;
    LineChart lineChart;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);


        // TODO: 8/2/16 change listview into linechart
        Intent intent = getIntent();
        priceArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        priceArray2 = new ArrayList<>();
        lineChart = (LineChart) findViewById(R.id.lineChart);

        listView = (ListView) findViewById(R.id.test);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, priceArray2);

        StockAsyncTask stockAsyncTask = new StockAsyncTask();
        stockAsyncTask.execute(intent.getStringExtra("Key"));

        priceArray.add(new Entry(3F, 0));
        priceArray.add(new Entry(10F, 1));
        priceArray.add(new Entry(1F, 2));
        priceArray.add(new Entry(22F, 3));
        priceArray.add(new Entry(9F, 4));

        dateArray.add("abc");
        dateArray.add("bbc");
        dateArray.add("cbc");
        dateArray.add("dbc");
        dateArray.add("ebc");

        listView.setAdapter(arrayAdapter);


        LineDataSet dataSet = new LineDataSet(priceArray, "Price of stock");
        LineData data = new LineData(dateArray, dataSet);
        lineChart.setData(data);


    }

    private class StockAsyncTask extends AsyncTask<String, Void, LineData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LineData doInBackground(String... params) {
            Stock stock = null;

            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.YEAR, -1); // from 1 years ago
            List<HistoricalQuote> historicalQuoteList = null;

            priceArray2.add("blah");

            try {
                stock = YahooFinance.get(params[0], from, to, Interval.WEEKLY);
                historicalQuoteList = stock.getHistory();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int j = historicalQuoteList.size()-1;
            ArrayList<String> testString = new ArrayList<>();


            for (int i = 0; i < historicalQuoteList.size(); i++) {
                String closing = historicalQuoteList.get(i).getClose().toString();
                Float closingPrice = Float.valueOf(closing);
                long date = historicalQuoteList.get(j).getDate().getTimeInMillis();
                String dateString = String.valueOf(date);
                priceArray.add(new Entry(closingPrice, i));
//                testString.add(dateString);
//                Collections.reverse(testString);
//                priceArray2.add(testString.get(j));
                j--;
                priceArray2.add(dateString);

//                priceArray2.add(dateArray.get(i) + "Position" + j + " " + closingPrice);
//                j++;
            }

//            LineDataSet dataSet = new LineDataSet(priceArray, "Price of stock");
//            LineData data = new LineData(dateArray, dataSet);
            return null;
        }

        @Override
        protected void onPostExecute(LineData data) {
            super.onPostExecute(data);
//            lineChart.setData(data);
            priceArray2.add("blah2");

            arrayAdapter.notifyDataSetChanged();
        }
    }

}
