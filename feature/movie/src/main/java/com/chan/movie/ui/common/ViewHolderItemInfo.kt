package com.chan.movie.ui.common

/**
 * position : 포지션 위치
 * positionY : Y축
 * viewWidth : 가로
 * viewHeight : 높이
 */
data class ViewHolderItemInfo(
    private var position: Int = 0,
    private var positionY: Float = 0.0f,
    private var viewWidth: Int = 0,
    private var viewHeight: Int = 0
) {

    fun itemPosition(): Int = position
    fun itemPositionY(): Int = positionY.toInt()
    fun itemViewHeight(): Int = viewHeight

    fun onChangedTouchItemView(position: Int, positionY: Float, viewWidth: Int, viewHeight: Int) {
        this.position = position
        this.positionY = positionY
        this.viewWidth = viewWidth
        this.viewHeight = viewHeight
    }
}
