package com.pranavj7.android.hellonote.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
/**
 * Created by pinkzz on 4/15/2017.
 */
public class rateme extends DialogFragment {

        private static final int LAUNCHES_UNTIL_PROMPT = 7;
        private static final String PREF_NAME = "APP_RATER";
        private static final String LAST_PROMPT = "LAST_PROMPT";
        private static final String LAUNCHES = "LAUNCHES";
        private static final String DISABLED = "DISABLED";

        public static void show(Context context, FragmentManager fragmentManager) {
            boolean shouldShow = false;
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            long currentTime = System.currentTimeMillis();
            long lastPromptTime = sharedPreferences.getLong(LAST_PROMPT, 0);
            if (lastPromptTime == 0) {
                lastPromptTime = currentTime;
                editor.putLong(LAST_PROMPT, lastPromptTime);
            }

            if (!sharedPreferences.getBoolean(DISABLED, false)) {
                int launches = sharedPreferences.getInt(LAUNCHES, 0) + 1;
                if (launches == LAUNCHES_UNTIL_PROMPT) {
                        shouldShow = true;

                }
                editor.putInt(LAUNCHES, launches);
            }

            if (shouldShow) {
                editor.putInt(LAUNCHES, 0).putLong(LAST_PROMPT, System.currentTimeMillis()).commit();
                new rateme().show(fragmentManager, null);
            } else {
                editor.apply();
            }
        }

        private static SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences(PREF_NAME, 0);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Rate the App!")
                    .setMessage("Hope you liked HelloNotes!\n" +
                            "please rate us!")

                    .setPositiveButton("rate now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getActivity().getPackageName())));
                            getSharedPreferences(getActivity()).edit().putBoolean(DISABLED, true).commit();
                            dismiss();
                        }
                    })
                    .setNeutralButton("remind later", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                        }
                    })
                    .setNegativeButton("never", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getSharedPreferences(getActivity()).edit().putBoolean(DISABLED, true).commit();
                            dismiss();
                        }
                    }).create();
        }
    }
