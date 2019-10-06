package com.detanexus.cafredirector;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent entryIntent = getIntent();
        Uri entryUri = entryIntent.getData();

        try {
            manageFile(entryUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent exitIntent = new Intent();
        exitIntent.setAction(Intent.ACTION_VIEW);
        exitIntent.addCategory(Intent.CATEGORY_DEFAULT);

        File filePath = new File(this.getFilesDir(), "media");
        File newFile = new File(filePath, "file.caf");
        Uri contentUri = FileProvider.getUriForFile(this, "com.detanexus.fileprovider", newFile);
        exitIntent.setDataAndType(contentUri, "audio/*");

        exitIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(exitIntent,PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo: resolveInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        if (resolveInfoList.size() > 1)
            startActivity(exitIntent);
        else
            Log.d(TAG, "Nothing found");
        finish();
    }

    private void manageFile(Uri entryUri) throws IOException {
        InputStream in = new ParcelFileDescriptor.AutoCloseInputStream(getContentResolver().openFileDescriptor(entryUri, "r"));
        File outFileDir = new File(getFilesDir(), "media");
        outFileDir.mkdir();
        File outFile = new File(outFileDir, "file.caf");
        outFile.delete();
        outFile.createNewFile();
        OutputStream out = new FileOutputStream(outFile);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
