package com.example.myapplication;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.CCFragment.CCNameFragment;
import com.example.myapplication.CCFragment.CCNumberFragment;
import com.example.myapplication.CCFragment.CCSecureCodeFragment;
import com.example.myapplication.CCFragment.CCValidityFragment;
import com.example.myapplication.Utils.CreditCardUtils;
import com.example.myapplication.Utils.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOutActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.btnNext)
    Button btnNext;
    DBConnect mydb;

    public CardFrontFragment cardFrontFragment;
    public CardBackFragment cardBackFragment;

    //This is our viewPager
    private ViewPager viewPager;

    CCNumberFragment numberFragment;
    CCNameFragment nameFragment;
    CCValidityFragment validityFragment;
    CCSecureCodeFragment secureCodeFragment;

    int total_item;
    boolean backTrack = false;

    private boolean mShowingBack = false;

    String cardNumber, cardCVV, cardValidity, cardName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        ButterKnife.bind(this);

        mydb = new DBConnect(this);

        cardFrontFragment = new CardFrontFragment();
        cardBackFragment = new CardBackFragment();

        if (savedInstanceState == null) {
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, cardFrontFragment).commit();

        } else {
            mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
        }

        getFragmentManager().addOnBackStackChangedListener(this);

        //Initializing viewPager
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == total_item)
                    btnNext.setText("SUBMIT");
                else
                    btnNext.setText("NEXT");

                Log.d("track", "onPageSelected: " + position);

                if (position == total_item) {
                    flipCard();
                    backTrack = true;
                } else if (position == total_item - 1 && backTrack) {
                    flipCard();
                    backTrack = false;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = viewPager.getCurrentItem();
                if (pos < total_item) {
                    viewPager.setCurrentItem(pos + 1);

                }
                else {
                        boolean isInserted = mydb.insertData(
                                nameFragment.getName(),
                                numberFragment.getCardNumber(),
                                validityFragment.getValidity(),
                                secureCodeFragment.getValue());

                        if (isInserted == true) {
                            Toast.makeText(CheckOutActivity.this, "Your Card is being Added", Toast.LENGTH_SHORT).show();
                            openActivity();
                        } else {
                            Toast.makeText(CheckOutActivity.this, "Card is not Added", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

        });

    }

    private void openActivity() {

        Toast.makeText(CheckOutActivity.this, "Your payment is successful", Toast.LENGTH_LONG).show();

        try {
            Thread.sleep(2000);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void checkEntries() {
        cardName = nameFragment.getName();
        cardNumber = numberFragment.getCardNumber();
        cardValidity = validityFragment.getValidity();
        cardCVV = secureCodeFragment.getValue();

        if (TextUtils.isEmpty(cardName)) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardNumber) || !CreditCardUtils.isValid(cardNumber.replace(" ",""))) {
            Toast.makeText(CheckOutActivity.this, "Enter Valid card number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardValidity)||!CreditCardUtils.isValidDate(cardValidity)) {
            Toast.makeText(CheckOutActivity.this, "Enter correct validity", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cardCVV)||cardCVV.length()<3) {
            Toast.makeText(CheckOutActivity.this, "Enter valid security number", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(CheckOutActivity.this, "Your card is added", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        numberFragment = new CCNumberFragment();
        nameFragment = new CCNameFragment();
        validityFragment = new CCValidityFragment();
        secureCodeFragment = new CCSecureCodeFragment();
        adapter.addFragment(numberFragment);
        adapter.addFragment(nameFragment);
        adapter.addFragment(validityFragment);
        adapter.addFragment(secureCodeFragment);

        total_item = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);

    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }
        // Flip to the back.
        //setCustomAnimations(int enter, int exit, int popEnter, int popExit)

        mShowingBack = true;

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.fragment_container, cardBackFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int pos = viewPager.getCurrentItem();
        if (pos > 0) {
            viewPager.setCurrentItem(pos - 1);
        } else
            super.onBackPressed();
    }

    public void nextClick() {
        btnNext.performClick();
    }

}
