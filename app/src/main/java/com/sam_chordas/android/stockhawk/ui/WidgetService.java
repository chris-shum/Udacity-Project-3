package com.sam_chordas.android.stockhawk.ui;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ShowMe on 8/15/16.
 */
public class WidgetService extends RemoteViewsService {
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new ListProvider(this.getApplicationContext(), intent));
    }

}