package com.udacity.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.activities.RecipesActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String UPDATE_ACTION = "com.udacity.bakingapp.widget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.udacity.bakingapp.widget.EXTRA_ITEM";


    // Called when the BroadcastReceiver receives an Intent broadcast.
    public void onReceive(Context context, Intent intent) {
        Log.d("DDD", "onReceive: ");

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            Log.d("DDD", "onReceive: Update Action");

            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
//            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();

//            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, IngredientsWidgetProvider.class));
            int appWidgetIds[] = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);
        }

        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("DDD", "onUpdate: ");

        // update each of the app widgets with the remote adapter
        for (int appWidgetId : appWidgetIds) {

            // Set up the intent that starts the IngredientsWidgetService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, IngredientsWidgetService.class);

            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects to a RemoteViewsService through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetId, R.id.listViewWidget, intent);

            // The empty view is displayed when the collection has no items.
            // It should be in the same layout used to instantiate the RemoteViews object above.
            rv.setEmptyView(R.id.listViewWidget, R.id.empty_view);

            //
            // Do additional processing specific to this app widget...
            //

//            // Create an Intent to launch RecipesActivity when clicked
//            Intent startActivityIntent = new Intent(context, RecipesActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setOnClickPendingIntent(R.id.listViewWidget, pendingIntent);

//            Intent updateIntent = new Intent(context, IngredientsWidgetProvider.class);
//            // Set the action for the intent.
//            updateIntent.setAction(IngredientsWidgetProvider.UPDATE_ACTION);
//            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setPendingIntentTemplate(R.id.listViewWidget, toastPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

