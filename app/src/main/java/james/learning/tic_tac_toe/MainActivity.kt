package james.learning.tic_tac_toe

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import james.learning.tic_tac_toe.databinding.ActivityMainBinding
import james.learning.tic_tac_toe.databinding.ResultDialogBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var activityView: ActivityMainBinding
    private lateinit var player1 : ArrayList<String>
    private lateinit var player2 : ArrayList<String>
    private var currentPlayer = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityView = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityView.root)
        player1 = ArrayList()
        player2 = ArrayList()
        activityView.view1.setOnClickListener(this)
        activityView.view2.setOnClickListener(this)
        activityView.view3.setOnClickListener(this)
        activityView.view4.setOnClickListener(this)
        activityView.view5.setOnClickListener(this)
        activityView.view6.setOnClickListener(this)
        activityView.view7.setOnClickListener(this)
        activityView.view8.setOnClickListener(this)
        activityView.view9.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view is ImageView && view.isClickable) {
            view.isClickable = false
            currentPlayer = if (currentPlayer == 1) {
                view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_x))
                player1.add(view.tag.toString())
                if(isWinner(player1))
                    return
                2
            } else {
                view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_o))
                player2.add(view.tag.toString())
                if (isWinner(player2))
                    return
                1
            }

            if (player1.size == 5 && player2.size == 4) {
                showDialog("Draw!")
            }
        }
    }

    private fun isWinner(list: ArrayList<String>): Boolean {
        if (hasValues(list)) {
            showDialog("Player $currentPlayer wins!")
            return true
        }
        return false
    }

    private fun hasValues(list: ArrayList<String>): Boolean {
        return (list.containsAll(arrayListOf("1", "2", "3"))) or list.containsAll(
            arrayListOf("4", "5", "6")) or list.containsAll(
            arrayListOf("7", "8", "9")) or list.containsAll(
            arrayListOf("1", "5", "9")) or list.containsAll(
            arrayListOf("3", "5", "7")) or list.containsAll(
            arrayListOf("1", "4", "7")) or list.containsAll(
            arrayListOf("2", "5", "8")) or list.containsAll(
            arrayListOf("3", "6", "9"))
    }

    private fun showDialog(text: String){
        val dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(false)
        val dialogView = ResultDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogView.root)
        dialog.window?.setLayout(getWindowWidth(), WindowManager.LayoutParams.WRAP_CONTENT)
        dialogView.resultText.text = text
        dialogView.cancelButton.setOnClickListener {
            clearView()
            dialog.dismiss()
        }
        dialogView.playAgainButton.setOnClickListener {
            clearView()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun clearView() {
        currentPlayer = 1
        player1 = ArrayList()
        player2 = ArrayList()
        for (view in activityView.gridLayout.children) {
            val imV = view as ImageView
            imV.setImageDrawable(null)
            imV.isClickable = true
        }
    }

    private fun getWindowWidth() = resources.displayMetrics.widthPixels - 20

}