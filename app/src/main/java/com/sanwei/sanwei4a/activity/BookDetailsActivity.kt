package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.drawee.view.SimpleDraweeView
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.book.BookQueryService
import com.sanwei.sanwei4a.presenter.PresenterBookDetails
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import com.sanwei.sanwei4a.util.FileHelper
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import com.sanwei.sanwei4a.view.IViewBookDetails
import com.stfalcon.frescoimageviewer.ImageViewer
import com.willy.ratingbar.ScaleRatingBar
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_book_details.*
import org.jetbrains.anko.*
import kotlin.math.max
import kotlin.math.min

class BookDetailsActivity : BaseActivity(), IViewBookDetails {

    override fun setIsLoading() {
        mEmptyView.visibility = View.VISIBLE
        mEmptyView.removeAllViews()
        mEmptyView.addView(EmptyViewBuilder.createEmptyForDetails(this,
                EmptyViewBuilder.EmptyType.TYPE_LOADING,
                View.OnClickListener {
                    mPresenter.request(mBooId)
                }))
    }

    override fun loadSuccess(info: BookDetailInfo) {
        mEmptyView.visibility = View.GONE
        Glide.with(this)
                .load(RequestUtil.getHeadPicAddr(info.accId, info.accPic))
                .apply(RequestOptions().error(R.drawable.ic_cheese).placeholder(R.drawable.ic_cheese))
                .into(z_img_user_book_details)
        (z_txt_points_book_details).text = StringBuffer("积分：${info.points}")
        mTxtUsername.text = info.accNickname
        mRatingBar.rating = info.accStars.toFloat()
        mTxtWatched.text = info.booWatched.toString()
        mIsFavor = info.isFavo
        mTxtFavored.text = info.booFava.toString()
        mTxtDescription.text = info.booDes
        mTxtDetails.text = info.bookDetail
        mTxtIsbn.text = info.booIsbn
        mTxtPage.text = info.bookPage.toString()
        mTxtPriceDev.text = info.bookPrice.toString()
        mTxtPublisher.text = info.bookPublisher
        mTxtCondition.text = info.booCon
        if (info.isFavo) mImgFavor.setImageResource(R.drawable.ic_favor_yes)

        val list = arrayListOf(RequestUtil.getBookPic(info.booFrontpic), RequestUtil.getBookPic(info.booInpic), RequestUtil.getBookPic(info.booEndpic))

        mBanner
                .setOnBannerListener {
                    ImageViewer.Builder(this, list)
                            .setStartPosition(it)
                            .show()
                }
                .setImages(list)
                .isAutoPlay(false)
                .setImageLoader(FrescoImageLoader())
                .start()


        mToolbar.title = info.bookName
        mToolbar.subtitle = info.bookAuthor

        if(App.account!=null){
            if(App.account!!.accId==info.accId){
                mBtnBorrow.visibility = View.GONE
            }
        }

        mBookDetails = info
    }

    override fun loadFail() {
        mEmptyView.visibility = View.VISIBLE
        mEmptyView.removeAllViews()
        mEmptyView.addView(EmptyViewBuilder.createEmptyForDetails(this,
                EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR,
                View.OnClickListener {
                    mPresenter.request(mBooId)
                }))
    }

    lateinit var mImgFavor: ImageView
    lateinit var mBanner: Banner
    lateinit var mBtnBorrow: Button
    var mIsFavor = false
    var mBooId: Int = -1
    var mShowType: Int = -1
    private lateinit var mBookDetails: BookDetailInfo

    private lateinit var mToolbar: Toolbar
    private lateinit var mTxtUsername: TextView
    private lateinit var mRatingBar: ScaleRatingBar
    private lateinit var mTxtWatched: TextView
    private lateinit var mTxtFavored: TextView
    private lateinit var mImgFavored: ImageView
    private lateinit var mTxtDescription: TextView
    private lateinit var mTxtDetails: TextView
    private lateinit var mTxtIsbn: TextView
    private lateinit var mTxtPage: TextView
    private lateinit var mTxtType: TextView
    private lateinit var mTxtPriceDev: TextView
    private lateinit var mTxtPublisher: TextView
    private lateinit var mTxtCondition: TextView
    private lateinit var mEmptyView: ViewGroup
    private var mRcBorrow: Int = 567

    private lateinit var mPresenter: PresenterBookDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        mToolbar = find(R.id.z_toolbar_book_details)
        mBanner = find(R.id.z_banner_book)
        mImgFavor = find(R.id.z_img_favor_book_details)
        mBtnBorrow = find(R.id.z_btn_borrow_book)
        mTxtUsername = find(R.id.z_txt_username_book_details)
        mRatingBar = find(R.id.z_rating_book_details)
        mTxtWatched = find(R.id.z_txt_watched_book_details)
        mTxtFavored = find(R.id.z_txt_favor_book_details)
        mImgFavored = find(R.id.z_img_favor_book_details)
        mTxtDescription = find(R.id.z_txt_description_book_details)
        mTxtDetails = find(R.id.z_txt_details_book_details)
        mTxtIsbn = find(R.id.z_txt_isbn_book_details)
        mTxtPage = find(R.id.z_txt_page_book_details)
        mTxtType = find(R.id.z_txt_type_book_details)
        mTxtPriceDev = find(R.id.z_txt_price_dev_book_details)
        mTxtPublisher = find(R.id.z_txt_publisher_book_details)
        mTxtCondition = find(R.id.z_txt_condition_book_details)
        mEmptyView = find(R.id.z_empty_book_details)

        mShowType = intent.getIntExtra("showType", -1)
        mBooId = intent.getIntExtra("booId", -1)

        mToolbar.setNavigationOnClickListener { finish() }

        mImgFavor.setOnClickListener {
            if (App.account != null && mBooId != -1) {
                val bookQueryService = BookQueryService()
                bookQueryService.doFavorite(mBooId, object : OnRequestListener<BookDetailInfo> {
                    override fun onSuccess(data: BookDetailInfo) {
                        mIsFavor = !mIsFavor
                        runOnUiThread {
                            mTxtFavored.text = data.booFava.toString()
                            if (mIsFavor) {
                                toast("已收藏")
                                mImgFavor.setImageResource(R.drawable.ic_favor_yes)
                            } else {
                                toast("已取消收藏")
                                mImgFavor.setImageResource(R.drawable.ic_favor_no)
                            }
                        }
                    }

                    override fun onFail(msg: String?, code: Int) {
                        Log.d(TAG, msg)
                    }
                })

            } else {
                toast("尚未登陆")
                //TODO 尚未登陆或网络错误的操作
            }
        }

        mBtnBorrow.setOnClickListener {
            when {
                App.account == null -> toast("尚未登录")
                mShowType == ShowType.bookDetails_default -> startActivityForResult<BorrowActivity>(mRcBorrow, "bookDetails" to mBookDetails)
                mShowType == ShowType.bookDetails_mine -> {
                    alert("撤销后该图书不再会被他人看到", "确定撤销图书？") {
                        positiveButton("确定") {
                            mPresenter.remove(mBooId)
                        }
                        negativeButton("取消") {}
                    }.show()

                }
                else -> toast("尚未实现")
            }
        }

        initCoverDescription()
        initScrollView()

        mPresenter = PresenterBookDetails()
        mPresenter.attachView(this)
        mPresenter.request(mBooId)

        initShowStyle()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mRcBorrow -> {
                if (resultCode == Activity.RESULT_OK) {
                    mBtnBorrow.visibility = View.GONE
                }
            }
        }
    }

    private fun initScrollView() {
        z_scroll_book_details.setOnScrollChangeListener { _, x, y, oldX, oldY ->
            val rate = y / dip(166).toFloat()
            mToolbar.alpha = max(min(rate, 1f), 0f)
        }
    }

    private fun initCoverDescription() {
        z_cover_description_book_details.setOnClickListener {
            if (!z_txt_details_book_details.text.isEmpty()) {
                if (z_txt_details_book_details.maxLines == 5) {
                    z_txt_details_book_details.maxLines = 100
                } else {
                    z_txt_details_book_details.maxLines = 5
                }
            }
        }
    }

    private fun initShowStyle() {
        when (mShowType) {
            ShowType.bookDetails_default -> {
                mBtnBorrow.text = "借     书"
            }
            ShowType.bookDetails_out -> {
                mBtnBorrow.visibility = View.GONE
            }
            ShowType.bookDetails_in -> {
                mBtnBorrow.text = "转     借"
            }
            ShowType.bookDetails_mine -> {
                mBtnBorrow.text = "撤     销"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    inner class FrescoImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            val uri = Uri.parse(path as String)
            imageView!!.setImageResource(R.drawable.ic_book_placeholder)
            imageView.setImageURI(uri)
        }

        override fun createImageView(context: Context?): ImageView {
            val simpleDraweeView = SimpleDraweeView(context)
            simpleDraweeView.setActualImageResource(R.drawable.ic_book_placeholder)
            return simpleDraweeView
        }
    }
}
