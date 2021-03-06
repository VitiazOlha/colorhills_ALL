package com.colorhills.all.view.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colorhills.all.R;
import com.colorhills.all.model.Image;
import com.colorhills.all.presenter.ImagePresenter;

import java.util.ArrayList;

import rx.Observable;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageFragment extends Fragment implements ImagePresenter.View {

    public static final String PAGE = "PAGE";
    public static final String LIST = "LIST";

    private int mPageNumber;
    private ImageView imageView;
    private ArrayList<Image> imageList;

    private ImagePresenter imagePresenter = new ImagePresenter();

    public static ImageFragment create(int pageNumber, ArrayList<Image> arrayList) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE, pageNumber);
        args.putSerializable(LIST, arrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(PAGE);
        imageList = (ArrayList<Image>) getArguments().getSerializable(LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_iv);

        Observable.just(imageList.get(mPageNumber).getTitle()).subscribe(((TextView) rootView.findViewById(R.id.image_tv))::setText);

        (rootView.findViewById(R.id.image_tv)).setOnClickListener(v -> Toast.makeText(getActivity(), imageList.get(mPageNumber).getTitle(), Toast.LENGTH_LONG).show());

        imagePresenter.initialize(ImageFragment.this, imageList.get(mPageNumber).getImage());
        imagePresenter.execute();

        return rootView;
    }

    @Override
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        new PhotoViewAttacher(imageView);
    }


}