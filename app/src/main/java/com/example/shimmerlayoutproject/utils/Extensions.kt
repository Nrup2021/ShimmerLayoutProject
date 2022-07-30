package com.example.shimmerlayoutproject.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.shimmerlayoutproject.dialog.ProgressBarDialogHelper

inline fun Activity.showProgressAlertDialog(func: ProgressBarDialogHelper.() -> Unit): AlertDialog =
    ProgressBarDialogHelper(this).apply {
        func()
    }.create()

inline fun Fragment.showProgressAlertDialog(func: ProgressBarDialogHelper.() -> Unit): AlertDialog =
    ProgressBarDialogHelper(this.requireContext()).apply {
        func()
    }.create()

