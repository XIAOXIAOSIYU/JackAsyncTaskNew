package com.jackzhao.www.jackasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class AsyncTaskHelper extends AsyncTask<Void, String, Void> {

    private ArrayAdapter<String> adapter;
    private ListView listView;
    private MainActivity mainActivity;
    private WeakReference ref_listview;
    private WeakReference ref_mainactivity;
    private ProgressDialog dialog;

    public AsyncTaskHelper(ListView lv, MainActivity ma) {

        ref_listview = new WeakReference(lv);
        ref_mainactivity = new WeakReference(ma);

        listView = (ListView) ref_listview.get();
        mainActivity = (MainActivity) ref_mainactivity.get();

        dialog = new ProgressDialog(mainActivity);
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (String item : DataHelper.data) {
            publishProgress(item);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        this.setDialogMessage("Data loading.....");
        adapter = (ArrayAdapter<String>) listView.getAdapter();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        adapter.add(values[0]);
    }

    private void setDialogMessage(String _message) {
        dialog.setTitle("Status");
        dialog.setMessage(_message);
        dialog.show();
    }

}
