package zhy.scau.com.personalmemo.bussiness.perform.widget.chart

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * Created by ZhengHy on 2017-08-13.
 */
class TachographView(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) :View(ctx, attrs, defStyleAttr){

    var mMaxNum : Int = 100;

    var mCurrentNum : Int = 75;

    lateinit var mPaint : Paint ;

    lateinit var mTotalPath : Path;

    lateinit var mCurrentPath : Path;

    lateinit var mPercentPath : Path

    lateinit var mPathMeasure : PathMeasure;

    var mTotalLength : Float = 0f;

    var mCurrentLength : Float = 0f;

    var mRadiud : Int = 0;

    var mViewHeight : Int = 0;

    var mViewWidth : Int =0;

    var mBgColor : Long = 0xff999999;

    var mBottomColor : Long = 0xffffffff;

    var mCurrentColor : Long = 0xFF778800;

    private val MAGICNUM : Float= 5 / 6.0f;

    private val CIRCLENUM : Float = 0.55181f;

    private var mAnimaValue : Float = 1f

    var mMaxNUm : Int = 100;

    var mCurNum : Int = 100

    var mChangeNum : Int = 0 ;

    var isAniming : Boolean = false

    constructor (ctx: Context, attrs: AttributeSet) : this(ctx, attrs, 0) {
        println("2 params")
    }

    constructor (ctx: Context) : this(ctx, null, 0) {
        println("1 params")

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        reCaculate();

    }

    fun reCaculate(){
        mViewWidth = measuredWidth;
        mViewHeight = measuredHeight;

        mRadiud = (Math.min(mViewHeight, mViewWidth) * MAGICNUM / 2).toInt();

        mTotalPath = Path();

        var midX : Int = mViewWidth / 2 ;
        var midy : Int = mViewHeight / 2 ;

        mTotalPath.moveTo((midX - mRadiud).toFloat(), (midy).toFloat());
        mTotalPath.cubicTo((midX - mRadiud).toFloat(), (midy).toFloat() - mRadiud * CIRCLENUM,
                midX - mRadiud * CIRCLENUM , (midy - mRadiud).toFloat(),
                midX.toFloat(), (midy - mRadiud).toFloat()
        );
        mTotalPath.cubicTo(midX + mRadiud * CIRCLENUM ,  (midy - mRadiud).toFloat(),
                (midX + mRadiud).toFloat(), (midy).toFloat() - mRadiud * CIRCLENUM,
                (midX + mRadiud).toFloat(), (midy).toFloat()
                );

        mPathMeasure = PathMeasure(mTotalPath, false);

        mTotalLength = mPathMeasure.length;

        mCurrentPath = Path();

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG);

    }


    fun setTheMaxNum(maxNUm : Int){
        mMaxNUm = maxNUm;
        mCurNum = maxNUm
        invalidate()
    }

    fun setCurrentNum(curNum : Int){
        mChangeNum = curNum - mCurNum;
        mCurNum = curNum
        performAnima()
    }

    private fun performAnima() {
        val change = mChangeNum * 1.0f / mMaxNUm
        val animator = ValueAnimator.ofFloat(0f, 1f);
        val tempAnimaValue  =  mAnimaValue;
        animator.addUpdateListener { animation ->

            var value : Float = animation.animatedValue as Float

            mAnimaValue =  value * change + tempAnimaValue;
            Log.d("animateeeee","mAnimaValue = " +mAnimaValue)
            invalidate()
            if(value == 1f){
                isAniming = false
            }
        }
        animator.duration = 500
        isAniming = true;
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mCurrentPath.reset();

        mCurrentPath.moveTo(0f, 0f)


        mCurrentLength = mAnimaValue * mTotalLength;

        mPathMeasure.getSegment(0f, mCurrentLength, mCurrentPath, true);

        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mBottomColor.toInt()
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth  = 50f


        canvas?.drawPath(mTotalPath, mPaint)

        mPaint.color = mCurrentColor.toInt()
        mPaint.strokeCap = Paint.Cap.ROUND

        canvas?.drawPath(mCurrentPath, mPaint)

    }

}