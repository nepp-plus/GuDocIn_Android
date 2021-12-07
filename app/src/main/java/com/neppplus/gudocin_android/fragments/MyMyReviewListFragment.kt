package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.MyReviewListRecyclerviewAdapter
import com.neppplus.gudocin_android.databinding.FragmentMymyReviewListBinding
import com.neppplus.gudocin_android.datas.ReviewData

class MyMyReviewListFragment : BaseFragment() {

    val mMyReivewList = ArrayList<ReviewData>()

    lateinit var binding : FragmentMymyReviewListBinding
    lateinit var mReivewRecyclerviewAdapter: MyReviewListRecyclerviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_mymy_review_list,container,false )

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()
        setupEvents()
    }
    override fun setupEvents() {

       mReivewRecyclerviewAdapter = MyReviewListRecyclerviewAdapter(mContext,mMyReivewList )
        binding.myReviewListRecyclerView.adapter = mReivewRecyclerviewAdapter
        binding.myReviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {

    }
}