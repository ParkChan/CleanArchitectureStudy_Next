package com.chan.movie.ui.common

/**
 * 선택한 아이템뷰의 하단 y축 위치 값과 높이정보
 */
data class ItemViewDisplayInfo(
    private var positionY: Float = 0.0f,
    private var viewHeight: Int = 0
) {

    fun itemViewBottomY(): Int = (positionY + viewHeight).toInt()
    fun itemViewHeight(): Int = viewHeight

    fun onChangedTouchItemView(positionY: Float, viewHeight: Int) {
        this.positionY = positionY
        this.viewHeight = viewHeight
    }
}
