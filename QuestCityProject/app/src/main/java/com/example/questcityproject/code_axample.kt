// Show quest stop dialog
fun showQuestStopDialog(context: Context) {
    val dialog = MaterialAlertDialogBuilder(context)
        .setView(R.layout.dialog_quest_stop)
        .create()

    dialog.show()

    dialog.findViewById<Button>(R.id.cancelButton)?.setOnClickListener {
        dialog.dismiss()
    }

    dialog.findViewById<Button>(R.id.stopButton)?.setOnClickListener {
        // Handle quest stopping
        dialog.dismiss()
    }
}

// Show message dialog
fun showMessageDialog(context: Context, message: String) {
    val dialog = MaterialAlertDialogBuilder(context)
        .setView(R.layout.dialog_message)
        .create()

    dialog.show()

    dialog.findViewById<TextView>(R.id.messageText)?.text = message
    dialog.findViewById<Button>(R.id.okButton)?.setOnClickListener {
        dialog.dismiss()
    }
}

// Show error dialog
fun showErrorDialog(context: Context, error: String) {
    val dialog = MaterialAlertDialogBuilder(context)
        .setView(R.layout.dialog_error)
        .create()

    dialog.show()

    dialog.findViewById<TextView>(R.id.errorText)?.text = error
    dialog.findViewById<Button>(R.id.okButton)?.setOnClickListener {
        dialog.dismiss()
    }
}

// Show loading indicator
fun showLoading(view: ImageView) {
    val drawable = ContextCompat.getDrawable(view.context, R.drawable.loading_animation) as AnimatedVectorDrawable
    view.setImageDrawable(drawable)
    drawable.start()
}