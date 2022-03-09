package com.chan.movie.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.chan.movie.databinding.DialogCommonBinding
import com.chan.ui.util.convertDpToPx

class CommonDialog(
    private val message: String,
    private val itemViewDisplayInfo: ViewHolderItemInfo
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
        if (itemViewDisplayInfo.itemPosition() == RECYCLER_VIEW_FIRST_ITEM) {
            viewLocationChangeOfTopAndBottomUI()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initDialogWindow()
        removeDialogPaddingAndDim()
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
        testInitDialogLocation2()
    }

    /**
     * 다이얼로그 높이를 정확히 알수 없을시 viewTreeObserver를 통한 측정
     */
    private fun testInitDialogLocation1() {
        var diff: Int
        binding.clRoot.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val dialogHeight = binding.clRoot.height
                    val listItemHeight = itemViewDisplayInfo.itemViewHeight()
                    diff = dialogHeight - listItemHeight
                    val params: WindowManager.LayoutParams? = dialog?.window?.attributes

                    dialog?.window?.attributes =
                        if (itemViewDisplayInfo.itemPosition() == RECYCLER_VIEW_FIRST_ITEM) {
                            params?.apply {
                                gravity = Gravity.TOP
                                width = ViewGroup.LayoutParams.MATCH_PARENT
                                height = ViewGroup.LayoutParams.WRAP_CONTENT
                                y = itemViewDisplayInfo
                                    .itemPositionY()
                                    .plus(itemViewDisplayInfo.itemViewHeight())
                            }
                        } else {
                            params?.apply {
                                gravity = Gravity.TOP
                                width = ViewGroup.LayoutParams.MATCH_PARENT
                                height = ViewGroup.LayoutParams.WRAP_CONTENT
                                y = itemViewDisplayInfo
                                    .itemPositionY()
                                    .plus(itemViewDisplayInfo.itemViewHeight())
                                    .minus(diff)
                            }
                        }
                    binding.clRoot.postDelayed({
                        binding.clRoot.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }, DELAY)
                }
            })
    }

    /**
     * 리스트 아이템과 다이얼로그의 높이가 고정일시 사용
     */
    private fun testInitDialogLocation2() {
        val listItemHeight = itemViewDisplayInfo.itemViewHeight()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes

        dialog?.window?.attributes =
            if (itemViewDisplayInfo.itemPosition() == RECYCLER_VIEW_FIRST_ITEM) {
                params?.apply {
                    gravity = Gravity.TOP
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    y = itemViewDisplayInfo.itemPositionY().plus(listItemHeight)
                }
            } else {
                params?.apply {
                    gravity = Gravity.TOP
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    y = itemViewDisplayInfo.itemPositionY()
                }
            }
    }

    private fun viewLocationChangeOfTopAndBottomUI() {
        val buttonGroupLayoutParams: ConstraintLayout.LayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(requireContext(), DIALOG_BUTTON_GROUP_VIEW_HEIGHT)
            ).apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToTop = binding.tvTitle.id
            }
        binding.clButtonGroup.layoutParams = buttonGroupLayoutParams

        val titleLayoutParams: ConstraintLayout.LayoutParams =
            ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                convertDpToPx(requireContext(), DIALOG_TITLE_VIEW_HEIGHT)
            ).apply {
                topToBottom = binding.clButtonGroup.id
            }
        binding.tvTitle.layoutParams = titleLayoutParams
    }

    private fun removeDialogPaddingAndDim() {
        dialog?.window?.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    companion object {
        private const val RECYCLER_VIEW_FIRST_ITEM = 0
        private const val DELAY = 1L
        private const val DIALOG_TITLE_VIEW_HEIGHT = 250
        private const val DIALOG_BUTTON_GROUP_VIEW_HEIGHT = 50
    }
}