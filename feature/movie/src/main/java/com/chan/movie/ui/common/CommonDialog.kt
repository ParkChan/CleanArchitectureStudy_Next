package com.chan.movie.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.chan.movie.databinding.DialogCommonBinding
import timber.log.Timber

class CommonDialog(
    private val message: String,
    private val itemViewDisplayInfo: ItemViewDisplayInfo
) : DialogFragment() {

    private lateinit var positiveListener: View.OnClickListener
    private lateinit var negativeListener: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    private lateinit var binding: DialogCommonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCommonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initDialogWindow()
        noneDefaultDialogPadding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = message
        binding.btnPositive.setOnClickListener(positiveListener)
        binding.btnNegative.setOnClickListener(negativeListener)
    }

    fun positiveListener(listener: View.OnClickListener) {
        positiveListener = listener
    }

    fun negativeListener(listener: View.OnClickListener) {
        negativeListener = listener
    }

    private fun initDialogWindow() {
        var diff: Int
        binding.clRoot.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    //[Start] 리스트 높이와 다이얼로그 높이가 고정으로 같으면 해당 코드 불필요
                    val dialogHeight = binding.clRoot.height
                    val listItemHeight = itemViewDisplayInfo.itemViewHeight()
                    //리스트 아이템보다 다이얼로그가 큰 경우
                    //다이얼로그 - 리스트 여분 만큼 Y축을 이동 시켜 주어야함
                    diff = dialogHeight - listItemHeight
                    Timber.d(">>>> diff $diff")
                    //[End] 리스트 높이와 다이얼로그 높이가 고정으로 같으면 해당 코드 불필요
                    val window = dialog?.window
                    val params: WindowManager.LayoutParams? = window?.attributes
                    params?.apply {
                        gravity = Gravity.TOP
                        width = ViewGroup.LayoutParams.MATCH_PARENT
                        height = ViewGroup.LayoutParams.WRAP_CONTENT
                        Timber.d(">>>> width is $width height is $height")
                        y = itemViewDisplayInfo.itemViewBottomY() - diff
                    }
                    dialog?.window?.attributes = params
                    binding.clRoot.postDelayed({
                        binding.clRoot.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }, DELAY)
                }
            })
    }

    private fun noneDefaultDialogPadding() {
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    companion object {
        private const val DELAY = 50L
    }
}