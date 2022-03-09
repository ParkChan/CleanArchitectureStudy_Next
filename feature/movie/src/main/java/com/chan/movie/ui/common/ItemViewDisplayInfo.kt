package com.chan.movie.ui.common

/**
 * 선택한 아이템뷰의 하단 y축 위치 값
 * 가로
 * 높이
 */
data class ItemViewDisplayInfo(
    private var position: Int = 0,
    private var positionY: Float = 0.0f,
    private var viewWidth: Int = 0,
    private var viewHeight: Int = 0
) {

    fun itemPosition(): Int = position
    fun itemPositionY(): Int = positionY.toInt()
    fun itemViewBottomY(): Int = (positionY + viewHeight).toInt()
    fun itemViewHeight(): Int = viewHeight

    fun onChangedTouchItemView(position: Int, positionY: Float, viewWidth: Int, viewHeight: Int) {
        this.position = position
        this.positionY = positionY
        this.viewWidth = viewWidth
        this.viewHeight = viewHeight
    }
}
