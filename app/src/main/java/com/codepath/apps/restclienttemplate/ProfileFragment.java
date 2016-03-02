package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.ProfileData;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements TwitterInterface.notifier {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "screenName";
    TextView name;
    TextView screenName;
    TextView followers;
    TextView following;
    TextView tweetCount;
    ImageView pic;
    TextView description;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLoadSuccess(ProfileData prof) {

        refreshProfile(prof);
    }

    private void refreshProfile(final ProfileData prof) {
        name.setText(prof.getName());
        screenName.setText(prof.getScreen_name());
        followers.setText("Followers: " + prof.getFollowers().toString());
        following.setText("Following: " + prof.getFollowing().toString());
        tweetCount.setText("#of Tweets: " + prof.getTweets().toString());
        description.setText(prof.getDescription());
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.switchUser(prof.getScreen_name());
            }
        });
        Picasso.with(getContext()).load(prof.getPic()).into(pic);
    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProfileData prof = TwitterInterface.getProfile(getContext(), mParam1, mParam2, this);

        View ret = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) ret.findViewById(R.id.profileName);
        screenName = (TextView) ret.findViewById(R.id.screenName);
        followers = (TextView) ret.findViewById(R.id.followers);
        following = (TextView) ret.findViewById(R.id.following);
        tweetCount = (TextView) ret.findViewById(R.id.teetCount);
        pic = (ImageView) ret.findViewById(R.id.profileImage);
        description = (TextView) ret.findViewById(R.id.tagLine);

        refreshProfile(prof);
        return ret;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        void switchUser(String screenName);
    }
}
