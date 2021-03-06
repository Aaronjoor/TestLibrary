package com.wangxiangle.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by wangxiangle on 2018/8/3 18:52
 * E-Mail Address： wang_x_le@163.com
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    final Builder builder;
    Paint mVerPaint, mHorPaint;


    public GridItemDecoration(Builder builder) {
        this.builder = builder;
        mVerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVerPaint.setStyle(Paint.Style.FILL);
        mVerPaint.setColor(builder.color);
        mHorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHorPaint.setStyle(Paint.Style.FILL);
        mHorPaint.setColor(builder.color);
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (builder.isHor) {
            drawHorizontal(c, parent);
        }
        drawVertical(c, parent);
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0 && builder.isHasHead) {
                continue;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + builder.size;
            c.drawRect(left, top, right, bottom, mVerPaint);

        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0 && builder.isHasHead) {
                continue;
            }
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + builder.size;
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin + builder.size;
            c.drawRect(left, top, right, bottom, mHorPaint);
        }
    }

    private Item getSpan(RecyclerView parent, int poition, View view) {
        Item item = new Item(1, 1, 0);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            int spanSize = spanSizeLookup.getSpanSize(poition);
            int spanIndex = ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanIndex(poition, spanCount);
            item.spanSize = spanSize;
            item.spanCount = spanCount;
            item.spanIndex = spanIndex;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            if (view != null) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                item.spanIndex = layoutParams.getSpanIndex();
            } else {
                item.spanIndex = 0;
            }
            item.spanCount = spanCount;
            item.spanSize = 1;
        }
        return item;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (itemPosition == 0 && builder.isHasHead) {
            return;
        }
        Item span = getSpan(parent, itemPosition, view);
        int spanCount = span.spanCount;
        int spanSize = span.spanSize;
        int spanIndex = span.spanIndex;

//        int left = spanIndex * builder.size / (spanCount);
//        int right = builder.size - (spanIndex + spanSize) * builder.size / (spanCount);
        int left = builder.size - spanIndex * builder.size / spanCount;
        int right = (spanIndex + spanSize) * builder.size / spanCount;
        int bottom = 0;
        int top = 0;
        if (builder.isHor) {
            bottom = builder.size;
            if (!builder.isHasHead) {
                //没有头部 画横线头部
                if (itemPosition < spanCount && builder.isDrawFirstTopLine) {
                    top = builder.size;
                }
            }
        }
        outRect.set(left, top, right, bottom);
    }


    public static class Builder {
        private Context c;
        int color;
        int size;
        boolean isHor;
        boolean isHasHead;
        boolean isDrawFirstTopLine =false;


        public Builder(Context c) {
            this.c = c;
            isHor = true;
            isHasHead = false;
            color = Color.WHITE;
            size = 20;
            isDrawFirstTopLine =false;
        }

        /**
         * 设置divider的颜色
         *
         * @param color
         * @return
         */
        public Builder color(@ColorInt int color) {
            this.color = color;
            return this;
        }

        /**
         * 是否画横着的线
         *
         * @param isPaint
         * @return
         */
        public Builder setHor(boolean isPaint) {
            this.isHor = isPaint;
            return this;
        }


        /**
         * 设置是否有头部
         *
         * @param isHasHead
         * @return
         */
        public Builder setHead(boolean isHasHead) {
            this.isHasHead = isHasHead;
            return this;
        }

        /**
         * 设置divider的宽度
         *
         * @param size
         * @return
         */
        public Builder size(int size) {
            this.size = size;
            return this;
        }


        /**
         *
         * @param drawFirstTopLine  true 画第一行顶部横线
         * @return
         */
        public Builder setDrawFirstTopLine(boolean drawFirstTopLine) {
            isDrawFirstTopLine = drawFirstTopLine;
            return this;
        }

        public GridItemDecoration build() {
            return new GridItemDecoration(this);
        }

    }

    class Item {
        int spanCount;
        int spanSize;
        int spanIndex;

        public Item(int spanCount, int spanSize, int spanIndex) {
            this.spanCount = spanCount;
            this.spanSize = spanSize;
            this.spanIndex = spanIndex;
        }
    }

}
