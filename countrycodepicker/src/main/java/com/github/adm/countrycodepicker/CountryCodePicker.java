package com.github.adm.countrycodepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


public class CountryCodePicker extends RelativeLayout implements PickerDialog.OnItemClick {


    Context mContext;
    private LayoutInflater mInflater;
    private View countryCodePicker;
    private AttributeSet attrs;

    private Country country;
    private TextView pickerNameCode;
    private TextView pickerPhoneCode;
    private ImageView pickerFlag;
    private ImageView pickerIcon;


    // CountryCodePicker Constructor ================
    public CountryCodePicker(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        inIt(null);

    }

    public CountryCodePicker(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        inIt(attrs);

    }

    public CountryCodePicker(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
        inIt(attrs);

    }


    // in hear custom CountryCodePicker component maneging
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void inIt(AttributeSet attrs) {
        mInflater = LayoutInflater.from(mContext);
        this.attrs = attrs;
        countryCodePicker = mInflater.inflate(R.layout.country_code_picker, this, true);

        getCountryList();

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountryCodePicker,
                0, 0);


        pickerFlag = findViewById(R.id.imageV_picker_flag);
        pickerNameCode = findViewById(R.id.textV_picker_nameCode);
        pickerPhoneCode = findViewById(R.id.textV_picker_phoneCode);
        pickerIcon = findViewById(R.id.imageV_picker_icon);


        showFlag(a.getBoolean(R.styleable.CountryCodePicker_showFlag, true));
        showText(a.getBoolean(R.styleable.CountryCodePicker_showText, true));
        showIcon(a.getBoolean(R.styleable.CountryCodePicker_showIcon, true));

        codeType(a.getInteger(R.styleable.CountryCodePicker_codeType, 1));
        setDefaultCountyNameCode(a.getString(R.styleable.CountryCodePicker_defaultCountyNameCode));


        float dimension = a.getDimension(R.styleable.CountryCodePicker_textSize, 14);
        setTextSize(dimension);

        int typeface = a.getInteger(R.styleable.CountryCodePicker_textStyle, 0);
        setTextStyle(typeface);

        setTextColor(a.getColor(R.styleable.CountryCodePicker_textColor, 0));
        setDropDownIconColor(a.getColor(R.styleable.CountryCodePicker_dropDownIconColor, 0));
        setDropDownIconSize(a.getDimension(R.styleable.CountryCodePicker_dropDownIconSize, 12));


        countryCodePicker.setOnClickListener(v -> {

            PickerDialog pickerDialog = new PickerDialog(mContext, countryList);
            pickerDialog.show();
            pickerDialog.setOnItemClick(this);

        });


    }


    // show and hide =======================================
    public void showText(boolean value) {
        pickerNameCode.setVisibility(value ? VISIBLE : GONE);
        pickerPhoneCode.setVisibility(value ? VISIBLE : GONE);
    }

    public void showFlag(boolean value) {
        pickerFlag.setVisibility(value ? VISIBLE : GONE);
    }

    public void showIcon(boolean value) {
        pickerIcon.setVisibility(value ? VISIBLE : GONE);
    }


    // set all method =======================================
    @SuppressLint("ResourceAsColor")
    private void setTextColor(int color) {

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountryCodePicker,
                0, 0);

        try {


            if (color != 0) {
                pickerNameCode.setTextColor(a.getColor(R.styleable.CountryCodePicker_textColor, color));
                pickerPhoneCode.setTextColor(a.getColor(R.styleable.CountryCodePicker_textColor, color));
            }

        } finally {
            a.recycle();
        }


    }

    public void setDropDownIconColor(int color) {

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountryCodePicker,
                0, 0);

        try {

            if (color != 0) {
                pickerIcon.setColorFilter(a.getColor(R.styleable.CountryCodePicker_dropDownIconColor, color));
            }

        } finally {
            a.recycle();
        }
    }

    public void setDropDownIconSize(float dimension) {


        if (dimension > 33.0f) {


            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ((int) (dimension / 5f) + 12), getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ((int) (dimension / 5f) + 6), getResources().getDisplayMetrics());

            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
            pickerIcon.setLayoutParams(parms);
            pickerIcon.requestLayout();

        }else if (dimension <= 33.0f) {



            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ((int) (dimension / 2.8f) + (12/2.8f)), getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    ((int) (dimension / 2.8f) + (6/2.8f)), getResources().getDisplayMetrics());

            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
            pickerIcon.setLayoutParams(parms);
            pickerIcon.requestLayout();



        }


    }


    public void codeType(int index) {


        if (index == 2) {
            pickerPhoneCode.setVisibility(GONE);

        } else if (index == 3) {
            pickerPhoneCode.setVisibility(VISIBLE);
            pickerNameCode.setVisibility(GONE);
            pickerNameCode.setPadding(0, 0, 8, 0);

        } else {
            pickerPhoneCode.setVisibility(VISIBLE);
            pickerNameCode.setVisibility(VISIBLE);
        }
    }


    float dim;

    public void setTextSize(float dimension) {

        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CountryCodePicker, 0, 0);
        try {


            if (dimension != 14.0f) {


                pickerNameCode.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION, dimension);
                pickerPhoneCode.setTextSize(TypedValue.COMPLEX_UNIT_FRACTION, dimension);

                if (dimension >= 49.5f) {


                    int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            ((int) (dimension / 5f) + 24), getResources().getDisplayMetrics());
                    int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            ((int) (dimension / 5f) + 16), getResources().getDisplayMetrics());

                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
                    pickerFlag.setLayoutParams(parms);
                    pickerFlag.requestLayout();


                    dim = width;


                }


            }


        } finally {
            a.recycle();
        }

    }


    public void setTextStyle(int typeface) {

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountryCodePicker,
                0, 0);

        try {


            if (typeface == Typeface.NORMAL) {
                pickerNameCode.setTypeface(pickerNameCode.getTypeface(), Typeface.NORMAL);
                pickerNameCode.setPadding(8, 0, 0, 0);

                pickerPhoneCode.setTypeface(pickerPhoneCode.getTypeface(), Typeface.NORMAL);
                pickerPhoneCode.setPadding(8, 0, 8, 0);

            } else if (typeface == Typeface.BOLD) {
                pickerNameCode.setTypeface(pickerNameCode.getTypeface(), Typeface.BOLD);
                pickerNameCode.setPadding(8, 0, 0, 0);

                pickerPhoneCode.setTypeface(pickerPhoneCode.getTypeface(), Typeface.BOLD);
                pickerPhoneCode.setPadding(8, 0, 8, 0);

            } else if (typeface == Typeface.BOLD_ITALIC) {
                pickerNameCode.setTypeface(pickerNameCode.getTypeface(), Typeface.BOLD_ITALIC);
                pickerNameCode.setPadding(8, 0, 0, 0);

                pickerPhoneCode.setTypeface(pickerPhoneCode.getTypeface(), Typeface.BOLD_ITALIC);
                pickerPhoneCode.setPadding(8, 0, 8, 0);

            } else if (typeface == Typeface.ITALIC) {

                pickerNameCode.setTypeface(pickerNameCode.getTypeface(), Typeface.ITALIC);
                pickerNameCode.setPadding(8, 0, 0, 0);

                pickerPhoneCode.setTypeface(pickerPhoneCode.getTypeface(), Typeface.ITALIC);
                pickerPhoneCode.setPadding(8, 0, 8, 0);
            }

        } finally {
            a.recycle();
        }
    }

    public void setDefaultCountyNameCode(String countryNameCode) {

        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CountryCodePicker,
                0, 0);

        try {

            if (countryNameCode == null) {
                this.country = getFilter("bd");
            } else if (countryNameCode.isEmpty()) {
                this.country = getFilter("bd");
            } else {
                this.country = getFilter(countryNameCode);
            }

            if (country != null) {

                pickerFlag.setImageResource(country.getCountryFlag());
                pickerNameCode.setText(country.getCountryNameCode());
                pickerPhoneCode.setText("+" + country.getCountryPhoneCode());
            }


        } finally {
            a.recycle();
        }

    }

    public void addOnCountryStateChangeListener(OnCountryStateChangeListener listener) {
        this.listener = listener;
    }


    public void setCountryName(String countryName) {
        country.setCountryName(countryName);
    }

    public void setCountryNameCode(String countryNameCode) {
        country.setCountryNameCode(countryNameCode);
    }

    public void setCountryPhoneCode(int countryPhoneCode) {
        country.setCountryPhoneCode("" + countryPhoneCode);
    }

    public void setCountryFlagResId(int resId) {
        country.setCountryFlag(resId);
    }


    // addOnChildAttachStateChangeListener
    // addOnCountryStateChangeListener

    // get all method  =======================================
    private int defaultFlagResId = 010;
    private List<Country> countryList = new ArrayList<>();

    private void getCountryList() {


        List<Country> countries = new ArrayList<>();
        countries.add(new Country(defaultFlagResId, "Andorra", "ad", "376"));
        countries.add(new Country(defaultFlagResId, "United Arab Emirates (UAE)", "ae", "971"));
        countries.add(new Country(defaultFlagResId, "Afghanistan", "af", "93"));
        countries.add(new Country(defaultFlagResId, "Antigua and Barbuda", "ag", "1"));
        countries.add(new Country(defaultFlagResId, "Anguilla", "ai", "1"));
        countries.add(new Country(defaultFlagResId, "Albania", "al", "355"));
        countries.add(new Country(defaultFlagResId, "Armenia", "am", "374"));
        countries.add(new Country(defaultFlagResId, "Angola", "ao", "244"));
        countries.add(new Country(defaultFlagResId, "Antarctica", "aq", "672"));
        countries.add(new Country(defaultFlagResId, "Argentina", "ar", "54"));
        countries.add(new Country(defaultFlagResId, "American Samoa", "as", "1"));
        countries.add(new Country(defaultFlagResId, "Austria", "at", "43"));
        countries.add(new Country(defaultFlagResId, "Australia", "au", "61"));
        countries.add(new Country(defaultFlagResId, "Aruba", "aw", "297"));
        countries.add(new Country(defaultFlagResId, "Åland Islands", "ax", "358"));
        countries.add(new Country(defaultFlagResId, "Azerbaijan", "az", "994"));
        countries.add(new Country(defaultFlagResId, "Bosnia And Herzegovina", "ba", "387"));
        countries.add(new Country(defaultFlagResId, "Barbados", "bb", "1"));
        countries.add(new Country(defaultFlagResId, "Bangladesh", "bd", "880"));
        countries.add(new Country(defaultFlagResId, "Belgium", "be", "32"));
        countries.add(new Country(defaultFlagResId, "Burkina Faso", "bf", "226"));
        countries.add(new Country(defaultFlagResId, "Bulgaria", "bg", "359"));
        countries.add(new Country(defaultFlagResId, "Bahrain", "bh", "973"));
        countries.add(new Country(defaultFlagResId, "Burundi", "bi", "257"));
        countries.add(new Country(defaultFlagResId, "Benin", "bj", "229"));
        countries.add(new Country(defaultFlagResId, "Saint Barthélemy", "bl", "590"));
        countries.add(new Country(defaultFlagResId, "Bermuda", "bm", "1"));
        countries.add(new Country(defaultFlagResId, "Brunei Darussalam", "bn", "673"));
        countries.add(new Country(defaultFlagResId, "Bolivia, Plurinational State Of", "bo", "591"));
        countries.add(new Country(defaultFlagResId, "Brazil", "br", "55"));
        countries.add(new Country(defaultFlagResId, "Bahamas", "bs", "1"));
        countries.add(new Country(defaultFlagResId, "Bhutan", "bt", "975"));
        countries.add(new Country(defaultFlagResId, "Botswana", "bw", "267"));
        countries.add(new Country(defaultFlagResId, "Belarus", "by", "375"));
        countries.add(new Country(defaultFlagResId, "Belize", "bz", "501"));
        countries.add(new Country(defaultFlagResId, "Canada", "ca", "1"));
        countries.add(new Country(defaultFlagResId, "Cocos (keeling) Islands", "cc", "61"));
        countries.add(new Country(defaultFlagResId, "Congo, The Democratic Republic Of The", "cd", "243"));
        countries.add(new Country(defaultFlagResId, "Central African Republic", "cf", "236"));
        countries.add(new Country(defaultFlagResId, "Congo", "cg", "242"));
        countries.add(new Country(defaultFlagResId, "Switzerland", "ch", "41"));
        countries.add(new Country(defaultFlagResId, "Côte D'ivoire", "ci", "225"));
        countries.add(new Country(defaultFlagResId, "Cook Islands", "ck", "682"));
        countries.add(new Country(defaultFlagResId, "Chile", "cl", "56"));
        countries.add(new Country(defaultFlagResId, "Cameroon", "cm", "237"));
        countries.add(new Country(defaultFlagResId, "China", "cn", "86"));
        countries.add(new Country(defaultFlagResId, "Colombia", "co", "57"));
        countries.add(new Country(defaultFlagResId, "Costa Rica", "cr", "506"));
        countries.add(new Country(defaultFlagResId, "Cuba", "cu", "53"));
        countries.add(new Country(defaultFlagResId, "Cape Verde", "cv", "238"));
        countries.add(new Country(defaultFlagResId, "Curaçao", "cw", "599"));
        countries.add(new Country(defaultFlagResId, "Christmas Island", "cx", "61"));
        countries.add(new Country(defaultFlagResId, "Cyprus", "cy", "357"));
        countries.add(new Country(defaultFlagResId, "Czech Republic", "cz", "420"));
        countries.add(new Country(defaultFlagResId, "Germany", "de", "49"));
        countries.add(new Country(defaultFlagResId, "Djibouti", "dj", "253"));
        countries.add(new Country(defaultFlagResId, "Denmark", "dk", "45"));
        countries.add(new Country(defaultFlagResId, "Dominica", "dm", "1"));
        countries.add(new Country(defaultFlagResId, "Dominican Republic", "do", "1"));
        countries.add(new Country(defaultFlagResId, "Algeria", "dz", "213"));
        countries.add(new Country(defaultFlagResId, "Ecuador", "ec", "593"));
        countries.add(new Country(defaultFlagResId, "Estonia", "ee", "372"));
        countries.add(new Country(defaultFlagResId, "Egypt", "eg", "20"));
        countries.add(new Country(defaultFlagResId, "Eritrea", "er", "291"));
        countries.add(new Country(defaultFlagResId, "Spain", "es", "34"));
        countries.add(new Country(defaultFlagResId, "Ethiopia", "et", "251"));
        countries.add(new Country(defaultFlagResId, "Finland", "fi", "358"));
        countries.add(new Country(defaultFlagResId, "Fiji", "fj", "679"));
        countries.add(new Country(defaultFlagResId, "Falkland Islands (malvinas)", "fk", "500"));
        countries.add(new Country(defaultFlagResId, "Micronesia, Federated States Of", "fm", "691"));
        countries.add(new Country(defaultFlagResId, "Faroe Islands", "fo", "298"));
        countries.add(new Country(defaultFlagResId, "France", "fr", "33"));
        countries.add(new Country(defaultFlagResId, "Gabon", "ga", "241"));
        countries.add(new Country(defaultFlagResId, "United Kingdom", "gb", "44"));
        countries.add(new Country(defaultFlagResId, "Grenada", "gd", "1"));
        countries.add(new Country(defaultFlagResId, "Georgia", "ge", "995"));
        countries.add(new Country(defaultFlagResId, "French Guyana", "gf", "594"));
        countries.add(new Country(defaultFlagResId, "Ghana", "gh", "233"));
        countries.add(new Country(defaultFlagResId, "Gibraltar", "gi", "350"));
        countries.add(new Country(defaultFlagResId, "Greenland", "gl", "299"));
        countries.add(new Country(defaultFlagResId, "Gambia", "gm", "220"));
        countries.add(new Country(defaultFlagResId, "Guinea", "gn", "224"));
        countries.add(new Country(defaultFlagResId, "Guadeloupe", "gp", "450"));
        countries.add(new Country(defaultFlagResId, "Equatorial Guinea", "gq", "240"));
        countries.add(new Country(defaultFlagResId, "Greece", "gr", "30"));
        countries.add(new Country(defaultFlagResId, "Guatemala", "gt", "502"));
        countries.add(new Country(defaultFlagResId, "Guam", "gu", "1"));
        countries.add(new Country(defaultFlagResId, "Guinea-bissau", "gw", "245"));
        countries.add(new Country(defaultFlagResId, "Guyana", "gy", "592"));
        countries.add(new Country(defaultFlagResId, "Hong Kong", "hk", "852"));
        countries.add(new Country(defaultFlagResId, "Honduras", "hn", "504"));
        countries.add(new Country(defaultFlagResId, "Croatia", "hr", "385"));
        countries.add(new Country(defaultFlagResId, "Haiti", "ht", "509"));
        countries.add(new Country(defaultFlagResId, "Hungary", "hu", "36"));
        countries.add(new Country(defaultFlagResId, "Indonesia", "id", "62"));
        countries.add(new Country(defaultFlagResId, "Ireland", "ie", "353"));
        countries.add(new Country(defaultFlagResId, "Israel", "il", "972"));
        countries.add(new Country(defaultFlagResId, "Isle Of Man", "im", "44"));
        countries.add(new Country(defaultFlagResId, "Iceland", "is", "354"));
        countries.add(new Country(defaultFlagResId, "India", "in", "91"));
        countries.add(new Country(defaultFlagResId, "British Indian Ocean Territory", "io", "246"));
        countries.add(new Country(defaultFlagResId, "Iraq", "iq", "964"));
        countries.add(new Country(defaultFlagResId, "Iran, Islamic Republic Of", "ir", "98"));
        countries.add(new Country(defaultFlagResId, "Italy", "it", "39"));
        countries.add(new Country(defaultFlagResId, "Jersey", "je", "44"));
        countries.add(new Country(defaultFlagResId, "Jamaica", "jm", "1"));
        countries.add(new Country(defaultFlagResId, "Jordan", "jo", "962"));
        countries.add(new Country(defaultFlagResId, "Japan", "jp", "81"));
        countries.add(new Country(defaultFlagResId, "Kenya", "ke", "254"));
        countries.add(new Country(defaultFlagResId, "Kyrgyzstan", "kg", "996"));
        countries.add(new Country(defaultFlagResId, "Cambodia", "kh", "855"));
        countries.add(new Country(defaultFlagResId, "Kiribati", "ki", "686"));
        countries.add(new Country(defaultFlagResId, "Comoros", "km", "269"));
        countries.add(new Country(defaultFlagResId, "Saint Kitts and Nevis", "kn", "1"));
        countries.add(new Country(defaultFlagResId, "North Korea", "kp", "850"));
        countries.add(new Country(defaultFlagResId, "South Korea", "kr", "82"));
        countries.add(new Country(defaultFlagResId, "Kuwait", "kw", "965"));
        countries.add(new Country(defaultFlagResId, "Cayman Islands", "ky", "1"));
        countries.add(new Country(defaultFlagResId, "Kazakhstan", "kz", "7"));
        countries.add(new Country(defaultFlagResId, "Lao People's Democratic Republic", "la", "856"));
        countries.add(new Country(defaultFlagResId, "Lebanon", "lb", "961"));
        countries.add(new Country(defaultFlagResId, "Saint Lucia", "lc", "1"));
        countries.add(new Country(defaultFlagResId, "Liechtenstein", "li", "423"));
        countries.add(new Country(defaultFlagResId, "Sri Lanka", "lk", "94"));
        countries.add(new Country(defaultFlagResId, "Liberia", "lr", "231"));
        countries.add(new Country(defaultFlagResId, "Lesotho", "ls", "266"));
        countries.add(new Country(defaultFlagResId, "Lithuania", "lt", "370"));
        countries.add(new Country(defaultFlagResId, "Luxembourg", "lu", "352"));
        countries.add(new Country(defaultFlagResId, "Latvia", "lv", "371"));
        countries.add(new Country(defaultFlagResId, "Libya", "ly", "218"));
        countries.add(new Country(defaultFlagResId, "Morocco", "ma", "212"));
        countries.add(new Country(defaultFlagResId, "Monaco", "mc", "377"));
        countries.add(new Country(defaultFlagResId, "Moldova, Republic Of", "md", "373"));
        countries.add(new Country(defaultFlagResId, "Montenegro", "me", "382"));
        countries.add(new Country(defaultFlagResId, "Saint Martin", "mf", "590"));
        countries.add(new Country(defaultFlagResId, "Madagascar", "mg", "261"));
        countries.add(new Country(defaultFlagResId, "Marshall Islands", "mh", "692"));
        countries.add(new Country(defaultFlagResId, "Macedonia (FYROM)", "mk", "389"));
        countries.add(new Country(defaultFlagResId, "Mali", "ml", "223"));
        countries.add(new Country(defaultFlagResId, "Myanmar", "mm", "95"));
        countries.add(new Country(defaultFlagResId, "Mongolia", "mn", "976"));
        countries.add(new Country(defaultFlagResId, "Macau", "mo", "853"));
        countries.add(new Country(defaultFlagResId, "Northern Mariana Islands", "mp", "1"));
        countries.add(new Country(defaultFlagResId, "Martinique", "mq", "596"));
        countries.add(new Country(defaultFlagResId, "Mauritania", "mr", "222"));
        countries.add(new Country(defaultFlagResId, "Montserrat", "ms", "1"));
        countries.add(new Country(defaultFlagResId, "Malta", "mt", "356"));
        countries.add(new Country(defaultFlagResId, "Mauritius", "mu", "230"));
        countries.add(new Country(defaultFlagResId, "Maldives", "mv", "960"));
        countries.add(new Country(defaultFlagResId, "Malawi", "mw", "265"));
        countries.add(new Country(defaultFlagResId, "Mexico", "mx", "52"));
        countries.add(new Country(defaultFlagResId, "Malaysia", "my", "60"));
        countries.add(new Country(defaultFlagResId, "Mozambique", "mz", "258"));
        countries.add(new Country(defaultFlagResId, "Namibia", "na", "264"));
        countries.add(new Country(defaultFlagResId, "New Caledonia", "nc", "687"));
        countries.add(new Country(defaultFlagResId, "Niger", "ne", "227"));
        countries.add(new Country(defaultFlagResId, "Norfolk Islands", "nf", "672"));
        countries.add(new Country(defaultFlagResId, "Nigeria", "ng", "234"));
        countries.add(new Country(defaultFlagResId, "Nicaragua", "ni", "505"));
        countries.add(new Country(defaultFlagResId, "Netherlands", "nl", "31"));
        countries.add(new Country(defaultFlagResId, "Norway", "no", "47"));
        countries.add(new Country(defaultFlagResId, "Nepal", "np", "977"));
        countries.add(new Country(defaultFlagResId, "Nauru", "nr", "674"));
        countries.add(new Country(defaultFlagResId, "Niue", "nu", "683"));
        countries.add(new Country(defaultFlagResId, "New Zealand", "nz", "64"));
        countries.add(new Country(defaultFlagResId, "Oman", "om", "968"));
        countries.add(new Country(defaultFlagResId, "Panama", "pa", "507"));
        countries.add(new Country(defaultFlagResId, "Peru", "pe", "51"));
        countries.add(new Country(defaultFlagResId, "French Polynesia", "pf", "689"));
        countries.add(new Country(defaultFlagResId, "Papua New Guinea", "pg", "675"));
        countries.add(new Country(defaultFlagResId, "Philippines", "ph", "63"));
        countries.add(new Country(defaultFlagResId, "Pakistan", "pk", "92"));
        countries.add(new Country(defaultFlagResId, "Poland", "pl", "48"));
        countries.add(new Country(defaultFlagResId, "Saint Pierre And Miquelon", "pm", "508"));
        countries.add(new Country(defaultFlagResId, "Pitcairn Islands", "pn", "870"));
        countries.add(new Country(defaultFlagResId, "Puerto Rico", "pr", "1"));
        countries.add(new Country(defaultFlagResId, "Palestine", "ps", "970"));
        countries.add(new Country(defaultFlagResId, "Portugal", "pt", "351"));
        countries.add(new Country(defaultFlagResId, "Palau", "pw", "680"));
        countries.add(new Country(defaultFlagResId, "Paraguay", "py", "595"));
        countries.add(new Country(defaultFlagResId, "Qatar", "qa", "974"));
        countries.add(new Country(defaultFlagResId, "Réunion", "re", "262"));
        countries.add(new Country(defaultFlagResId, "Romania", "ro", "40"));
        countries.add(new Country(defaultFlagResId, "Serbia", "rs", "381"));
        countries.add(new Country(defaultFlagResId, "Russian Federation", "ru", "7"));
        countries.add(new Country(defaultFlagResId, "Rwanda", "rw", "250"));
        countries.add(new Country(defaultFlagResId, "Saudi Arabia", "sa", "966"));
        countries.add(new Country(defaultFlagResId, "Solomon Islands", "sb", "677"));
        countries.add(new Country(defaultFlagResId, "Seychelles", "sc", "248"));
        countries.add(new Country(defaultFlagResId, "v", "sd", "249"));
        countries.add(new Country(defaultFlagResId, "Sweden", "se", "46"));
        countries.add(new Country(defaultFlagResId, "Singapore", "sg", "65"));
        countries.add(new Country(defaultFlagResId, "Saint Helena,Tristan Da Cunha", "sh", "290"));
        countries.add(new Country(defaultFlagResId, "Slovenia", "si", "386"));
        countries.add(new Country(defaultFlagResId, "Slovakia", "sk", "421"));
        countries.add(new Country(defaultFlagResId, "Sierra Leone", "sl", "232"));
        countries.add(new Country(defaultFlagResId, "San Marino", "sm", "378"));
        countries.add(new Country(defaultFlagResId, "Senegal", "sn", "221"));
        countries.add(new Country(defaultFlagResId, "Somalia", "so", "252"));
        countries.add(new Country(defaultFlagResId, "Suriname", "sr", "597"));
        countries.add(new Country(defaultFlagResId, "South Sudan", "ss", "211"));
        countries.add(new Country(defaultFlagResId, "Sao Tome And Principe", "st", "239"));
        countries.add(new Country(defaultFlagResId, "El Salvador", "sv", "503"));
        countries.add(new Country(defaultFlagResId, "Sint Maarten", "sx", "1"));
        countries.add(new Country(defaultFlagResId, "Syrian Arab Republic", "sy", "963"));
        countries.add(new Country(defaultFlagResId, "Swaziland", "sz", "268"));
        countries.add(new Country(defaultFlagResId, "Turks and Caicos Islands", "tc", "1"));
        countries.add(new Country(defaultFlagResId, "Chad", "td", "235"));
        countries.add(new Country(defaultFlagResId, "Togo", "tg", "228"));
        countries.add(new Country(defaultFlagResId, "Thailand", "th", "66"));
        countries.add(new Country(defaultFlagResId, "Tajikistan", "tj", "992"));
        countries.add(new Country(defaultFlagResId, "Tokelau", "tk", "690"));
        countries.add(new Country(defaultFlagResId, "Timor-leste", "tl", "670"));
        countries.add(new Country(defaultFlagResId, "Turkmenistan", "tm", "993"));
        countries.add(new Country(defaultFlagResId, "Tunisia", "tn", "216"));
        countries.add(new Country(defaultFlagResId, "Tonga", "to", "676"));
        countries.add(new Country(defaultFlagResId, "Turkey", "tr", "90"));
        countries.add(new Country(defaultFlagResId, "Trinidad &amp", "tt", "1"));
        countries.add(new Country(defaultFlagResId, "Tuvalu", "tv", "688"));
        countries.add(new Country(defaultFlagResId, "Taiwan", "tw", "886"));
        countries.add(new Country(defaultFlagResId, "Tanzania, United Republic Of", "tz", "255"));
        countries.add(new Country(defaultFlagResId, "Ukraine", "ua", "380"));
        countries.add(new Country(defaultFlagResId, "Uganda", "ug", "256"));
        countries.add(new Country(defaultFlagResId, "United States", "us", "1"));
        countries.add(new Country(defaultFlagResId, "Uruguay", "uy", "598"));
        countries.add(new Country(defaultFlagResId, "Uzbekistan", "uz", "998"));
        countries.add(new Country(defaultFlagResId, "Holy See (vatican City State)", "va", "379"));
        countries.add(new Country(defaultFlagResId, "Saint Vincent &amp", "vc", "1"));
        countries.add(new Country(defaultFlagResId, "Venezuela", "ve", "58"));
        countries.add(new Country(defaultFlagResId, "British Virgin Islands", "vg", "1"));
        countries.add(new Country(defaultFlagResId, "US Virgin Islands", "vi", "1"));
        countries.add(new Country(defaultFlagResId, "Vietnam", "vn", "84"));
        countries.add(new Country(defaultFlagResId, "Vanuatu", "vu", "678"));
        countries.add(new Country(defaultFlagResId, "Wallis And Futuna", "wf", "681"));
        countries.add(new Country(defaultFlagResId, "Samoa", "ws", "685"));
        countries.add(new Country(defaultFlagResId, "Kosovo", "xk", "383"));
        countries.add(new Country(defaultFlagResId, "Yemen", "ye", "967"));
        countries.add(new Country(defaultFlagResId, "Mayotte", "yt", "262"));
        countries.add(new Country(defaultFlagResId, "South Africa", "za", "27"));
        countries.add(new Country(defaultFlagResId, "Zambia", "zm", "260"));
        countries.add(new Country(defaultFlagResId, "Zimbabwe", "zw", "263"));
        this.countryList = countries;


    }

    private int getFlagResID(Country country) {
        switch (country.getCountryNameCode().toLowerCase()) {
            case "ad": //andorra
                return R.drawable.flag_andorra;
            case "ae": //united arab emirates
                return R.drawable.flag_uae;
            case "af": //afghanistan
                return R.drawable.flag_afghanistan;
            case "ag": //antigua & barbuda
                return R.drawable.flag_antigua_and_barbuda;
            case "ai": //anguilla // Caribbean Islands
                return R.drawable.flag_anguilla;
            case "al": //albania
                return R.drawable.flag_albania;
            case "am": //armenia
                return R.drawable.flag_armenia;
            case "ao": //angola
                return R.drawable.flag_angola;
            case "aq": //antarctica // custom
                return R.drawable.flag_antarctica;
            case "ar": //argentina
                return R.drawable.flag_argentina;
            case "as": //American Samoa
                return R.drawable.flag_american_samoa;
            case "at": //austria
                return R.drawable.flag_austria;
            case "au": //australia
                return R.drawable.flag_australia;
            case "aw": //aruba
                return R.drawable.flag_aruba;
            case "ax": //alan islands
                return R.drawable.flag_aland;
            case "az": //azerbaijan
                return R.drawable.flag_azerbaijan;
            case "ba": //bosnia and herzegovina
                return R.drawable.flag_bosnia;
            case "bb": //barbados
                return R.drawable.flag_barbados;
            case "bd": //bangladesh
                return R.drawable.flag_bangladesh;
            case "be": //belgium
                return R.drawable.flag_belgium;
            case "bf": //burkina faso
                return R.drawable.flag_burkina_faso;
            case "bg": //bulgaria
                return R.drawable.flag_bulgaria;
            case "bh": //bahrain
                return R.drawable.flag_bahrain;
            case "bi": //burundi
                return R.drawable.flag_burundi;
            case "bj": //benin
                return R.drawable.flag_benin;
            case "bl": //saint barthélemy
                return R.drawable.flag_saint_barthelemy;// custom
            case "bm": //bermuda
                return R.drawable.flag_bermuda;
            case "bn": //brunei darussalam // custom
                return R.drawable.flag_brunei;
            case "bo": //bolivia, plurinational state of
                return R.drawable.flag_bolivia;
            case "br": //brazil
                return R.drawable.flag_brazil;
            case "bs": //bahamas
                return R.drawable.flag_bahamas;
            case "bt": //bhutan
                return R.drawable.flag_bhutan;
            case "bw": //botswana
                return R.drawable.flag_botswana;
            case "by": //belarus
                return R.drawable.flag_belarus;
            case "bz": //belize
                return R.drawable.flag_belize;
            case "ca": //canada
                return R.drawable.flag_canada;
            case "cc": //cocos (keeling) islands
                return R.drawable.flag_cocos;// custom
            case "cd": //congo, the democratic republic of the
                return R.drawable.flag_democratic_republic_of_the_congo;
            case "cf": //central african republic
                return R.drawable.flag_central_african_republic;
            case "cg": //congo
                return R.drawable.flag_republic_of_the_congo;
            case "ch": //switzerland
                return R.drawable.flag_switzerland;
            case "ci": //côte d\'ivoire
                return R.drawable.flag_cote_divoire;
            case "ck": //cook islands
                return R.drawable.flag_cook_islands;
            case "cl": //chile
                return R.drawable.flag_chile;
            case "cm": //cameroon
                return R.drawable.flag_cameroon;
            case "cn": //china
                return R.drawable.flag_china;
            case "co": //colombia
                return R.drawable.flag_colombia;
            case "cr": //costa rica
                return R.drawable.flag_costa_rica;
            case "cu": //cuba
                return R.drawable.flag_cuba;
            case "cv": //cape verde
                return R.drawable.flag_cape_verde;
            case "cw": //curaçao
                return R.drawable.flag_curacao;
            case "cx": //christmas island
                return R.drawable.flag_christmas_island;
            case "cy": //cyprus
                return R.drawable.flag_cyprus;
            case "cz": //czech republic
                return R.drawable.flag_czech_republic;
            case "de": //germany
                return R.drawable.flag_germany;
            case "dj": //djibouti
                return R.drawable.flag_djibouti;
            case "dk": //denmark
                return R.drawable.flag_denmark;
            case "dm": //dominica
                return R.drawable.flag_dominica;
            case "do": //dominican republic
                return R.drawable.flag_dominican_republic;
            case "dz": //algeria
                return R.drawable.flag_algeria;
            case "ec": //ecuador
                return R.drawable.flag_ecuador;
            case "ee": //estonia
                return R.drawable.flag_estonia;
            case "eg": //egypt
                return R.drawable.flag_egypt;
            case "er": //eritrea
                return R.drawable.flag_eritrea;
            case "es": //spain
                return R.drawable.flag_spain;
            case "et": //ethiopia
                return R.drawable.flag_ethiopia;
            case "fi": //finland
                return R.drawable.flag_finland;
            case "fj": //fiji
                return R.drawable.flag_fiji;
            case "fk": //falkland islands (malvinas)
                return R.drawable.flag_falkland_islands;
            case "fm": //micronesia, federated states of
                return R.drawable.flag_micronesia;
            case "fo": //faroe islands
                return R.drawable.flag_faroe_islands;
            case "fr": //france
                return R.drawable.flag_france;
            case "ga": //gabon
                return R.drawable.flag_gabon;
            case "gb": //united kingdom
                return R.drawable.flag_united_kingdom;
            case "gd": //grenada
                return R.drawable.flag_grenada;
            case "ge": //georgia
                return R.drawable.flag_georgia;
            case "gf": //guyane
                return R.drawable.flag_guyane;
            case "gg": //Guernsey
                return R.drawable.flag_guernsey;
            case "gh": //ghana
                return R.drawable.flag_ghana;
            case "gi": //gibraltar
                return R.drawable.flag_gibraltar;
            case "gl": //greenland
                return R.drawable.flag_greenland;
            case "gm": //gambia
                return R.drawable.flag_gambia;
            case "gn": //guinea
                return R.drawable.flag_guinea;
            case "gp": //guadeloupe
                return R.drawable.flag_guadeloupe;
            case "gq": //equatorial guinea
                return R.drawable.flag_equatorial_guinea;
            case "gr": //greece
                return R.drawable.flag_greece;
            case "gt": //guatemala
                return R.drawable.flag_guatemala;
            case "gu": //Guam
                return R.drawable.flag_guam;
            case "gw": //guinea-bissau
                return R.drawable.flag_guinea_bissau;
            case "gy": //guyana
                return R.drawable.flag_guyana;
            case "hk": //hong kong
                return R.drawable.flag_hong_kong;
            case "hn": //honduras
                return R.drawable.flag_honduras;
            case "hr": //croatia
                return R.drawable.flag_croatia;
            case "ht": //haiti
                return R.drawable.flag_haiti;
            case "hu": //hungary
                return R.drawable.flag_hungary;
            case "id": //indonesia
                return R.drawable.flag_indonesia;
            case "ie": //ireland
                return R.drawable.flag_ireland;
            case "il": //israel
                return R.drawable.flag_israel;
            case "im": //isle of man
                return R.drawable.flag_isleof_man; // custom
            case "is": //Iceland
                return R.drawable.flag_iceland;
            case "in": //india
                return R.drawable.flag_india;
            case "io": //British indian ocean territory
                return R.drawable.flag_british_indian_ocean_territory;
            case "iq": //iraq
                return R.drawable.flag_iraq_new;
            case "ir": //iran, islamic republic of
                return R.drawable.flag_iran;
            case "it": //italy
                return R.drawable.flag_italy;
            case "je": //Jersey
                return R.drawable.flag_jersey;
            case "jm": //jamaica
                return R.drawable.flag_jamaica;
            case "jo": //jordan
                return R.drawable.flag_jordan;
            case "jp": //japan
                return R.drawable.flag_japan;
            case "ke": //kenya
                return R.drawable.flag_kenya;
            case "kg": //kyrgyzstan
                return R.drawable.flag_kyrgyzstan;
            case "kh": //cambodia
                return R.drawable.flag_cambodia;
            case "ki": //kiribati
                return R.drawable.flag_kiribati;
            case "km": //comoros
                return R.drawable.flag_comoros;
            case "kn": //st kitts & nevis
                return R.drawable.flag_saint_kitts_and_nevis;
            case "kp": //north korea
                return R.drawable.flag_north_korea;
            case "kr": //south korea
                return R.drawable.flag_south_korea;
            case "kw": //kuwait
                return R.drawable.flag_kuwait;
            case "ky": //Cayman_Islands
                return R.drawable.flag_cayman_islands;
            case "kz": //kazakhstan
                return R.drawable.flag_kazakhstan;
            case "la": //lao people\'s democratic republic
                return R.drawable.flag_laos;
            case "lb": //lebanon
                return R.drawable.flag_lebanon;
            case "lc": //st lucia
                return R.drawable.flag_saint_lucia;
            case "li": //liechtenstein
                return R.drawable.flag_liechtenstein;
            case "lk": //sri lanka
                return R.drawable.flag_sri_lanka;
            case "lr": //liberia
                return R.drawable.flag_liberia;
            case "ls": //lesotho
                return R.drawable.flag_lesotho;
            case "lt": //lithuania
                return R.drawable.flag_lithuania;
            case "lu": //luxembourg
                return R.drawable.flag_luxembourg;
            case "lv": //latvia
                return R.drawable.flag_latvia;
            case "ly": //libya
                return R.drawable.flag_libya;
            case "ma": //morocco
                return R.drawable.flag_morocco;
            case "mc": //monaco
                return R.drawable.flag_monaco;
            case "md": //moldova, republic of
                return R.drawable.flag_moldova;
            case "me": //montenegro
                return R.drawable.flag_of_montenegro;// custom
            case "mf":
                return R.drawable.flag_saint_martin;
            case "mg": //madagascar
                return R.drawable.flag_madagascar;
            case "mh": //marshall islands
                return R.drawable.flag_marshall_islands;
            case "mk": //macedonia, the former yugoslav republic of
                return R.drawable.flag_macedonia;
            case "ml": //mali
                return R.drawable.flag_mali;
            case "mm": //myanmar
                return R.drawable.flag_myanmar;
            case "mn": //mongolia
                return R.drawable.flag_mongolia;
            case "mo": //macao
                return R.drawable.flag_macao;
            case "mp": // Northern mariana islands
                return R.drawable.flag_northern_mariana_islands;
            case "mq": //martinique
                return R.drawable.flag_martinique;
            case "mr": //mauritania
                return R.drawable.flag_mauritania;
            case "ms": //montserrat
                return R.drawable.flag_montserrat;
            case "mt": //malta
                return R.drawable.flag_malta;
            case "mu": //mauritius
                return R.drawable.flag_mauritius;
            case "mv": //maldives
                return R.drawable.flag_maldives;
            case "mw": //malawi
                return R.drawable.flag_malawi;
            case "mx": //mexico
                return R.drawable.flag_mexico;
            case "my": //malaysia
                return R.drawable.flag_malaysia;
            case "mz": //mozambique
                return R.drawable.flag_mozambique;
            case "na": //namibia
                return R.drawable.flag_namibia;
            case "nc": //new caledonia
                return R.drawable.flag_new_caledonia;// custom
            case "ne": //niger
                return R.drawable.flag_niger;
            case "nf": //Norfolk
                return R.drawable.flag_norfolk_island;
            case "ng": //nigeria
                return R.drawable.flag_nigeria;
            case "ni": //nicaragua
                return R.drawable.flag_nicaragua;
            case "nl": //netherlands
                return R.drawable.flag_netherlands;
            case "no": //norway
                return R.drawable.flag_norway;
            case "np": //nepal
                return R.drawable.flag_nepal;
            case "nr": //nauru
                return R.drawable.flag_nauru;
            case "nu": //niue
                return R.drawable.flag_niue;
            case "nz": //new zealand
                return R.drawable.flag_new_zealand;
            case "om": //oman
                return R.drawable.flag_oman;
            case "pa": //panama
                return R.drawable.flag_panama;
            case "pe": //peru
                return R.drawable.flag_peru;
            case "pf": //french polynesia
                return R.drawable.flag_french_polynesia;
            case "pg": //papua new guinea
                return R.drawable.flag_papua_new_guinea;
            case "ph": //philippines
                return R.drawable.flag_philippines;
            case "pk": //pakistan
                return R.drawable.flag_pakistan;
            case "pl": //poland
                return R.drawable.flag_poland;
            case "pm": //saint pierre and miquelon
                return R.drawable.flag_saint_pierre;
            case "pn": //pitcairn
                return R.drawable.flag_pitcairn_islands;
            case "pr": //puerto rico
                return R.drawable.flag_puerto_rico;
            case "ps": //palestine
                return R.drawable.flag_palestine;
            case "pt": //portugal
                return R.drawable.flag_portugal;
            case "pw": //palau
                return R.drawable.flag_palau;
            case "py": //paraguay
                return R.drawable.flag_paraguay;
            case "qa": //qatar
                return R.drawable.flag_qatar;
            case "re": //la reunion
                return R.drawable.flag_martinique; // no exact flag found
            case "ro": //romania
                return R.drawable.flag_romania;
            case "rs": //serbia
                return R.drawable.flag_serbia; // custom
            case "ru": //russian federation
                return R.drawable.flag_russian_federation;
            case "rw": //rwanda
                return R.drawable.flag_rwanda;
            case "sa": //saudi arabia
                return R.drawable.flag_saudi_arabia;
            case "sb": //solomon islands
                return R.drawable.flag_soloman_islands;
            case "sc": //seychelles
                return R.drawable.flag_seychelles;
            case "sd": //sudan
                return R.drawable.flag_sudan;
            case "se": //sweden
                return R.drawable.flag_sweden;
            case "sg": //singapore
                return R.drawable.flag_singapore;
            case "sh": //saint helena, ascension and tristan da cunha
                return R.drawable.flag_saint_helena; // custom
            case "si": //slovenia
                return R.drawable.flag_slovenia;
            case "sk": //slovakia
                return R.drawable.flag_slovakia;
            case "sl": //sierra leone
                return R.drawable.flag_sierra_leone;
            case "sm": //san marino
                return R.drawable.flag_san_marino;
            case "sn": //senegal
                return R.drawable.flag_senegal;
            case "so": //somalia
                return R.drawable.flag_somalia;
            case "sr": //suriname
                return R.drawable.flag_suriname;
            case "ss": //south sudan
                return R.drawable.flag_south_sudan;
            case "st": //sao tome and principe
                return R.drawable.flag_sao_tome_and_principe;
            case "sv": //el salvador
                return R.drawable.flag_el_salvador;
            case "sx": //sint maarten
                return R.drawable.flag_sint_maarten;
            case "sy": //syrian arab republic
                return R.drawable.flag_syria;
            case "sz": //swaziland
                return R.drawable.flag_swaziland;
            case "tc": //turks & caicos islands
                return R.drawable.flag_turks_and_caicos_islands;
            case "td": //chad
                return R.drawable.flag_chad;
            case "tg": //togo
                return R.drawable.flag_togo;
            case "th": //thailand
                return R.drawable.flag_thailand;
            case "tj": //tajikistan
                return R.drawable.flag_tajikistan;
            case "tk": //tokelau
                return R.drawable.flag_tokelau; // custom
            case "tl": //timor-leste
                return R.drawable.flag_timor_leste;
            case "tm": //turkmenistan
                return R.drawable.flag_turkmenistan;
            case "tn": //tunisia
                return R.drawable.flag_tunisia;
            case "to": //tonga
                return R.drawable.flag_tonga;
            case "tr": //turkey
                return R.drawable.flag_turkey;
            case "tt": //trinidad & tobago
                return R.drawable.flag_trinidad_and_tobago;
            case "tv": //tuvalu
                return R.drawable.flag_tuvalu;
            case "tw": //taiwan, province of china
                return R.drawable.flag_taiwan;
            case "tz": //tanzania, united republic of
                return R.drawable.flag_tanzania;
            case "ua": //ukraine
                return R.drawable.flag_ukraine;
            case "ug": //uganda
                return R.drawable.flag_uganda;
            case "us": //united states
                return R.drawable.flag_united_states_of_america;
            case "uy": //uruguay
                return R.drawable.flag_uruguay;
            case "uz": //uzbekistan
                return R.drawable.flag_uzbekistan;
            case "va": //holy see (vatican city state)
                return R.drawable.flag_vatican_city;
            case "vc": //st vincent & the grenadines
                return R.drawable.flag_saint_vicent_and_the_grenadines;
            case "ve": //venezuela, bolivarian republic of
                return R.drawable.flag_venezuela;
            case "vg": //british virgin islands
                return R.drawable.flag_british_virgin_islands;
            case "vi": //us virgin islands
                return R.drawable.flag_us_virgin_islands;
            case "vn": //vietnam
                return R.drawable.flag_vietnam;
            case "vu": //vanuatu
                return R.drawable.flag_vanuatu;
            case "wf": //wallis and futuna
                return R.drawable.flag_wallis_and_futuna;
            case "ws": //samoa
                return R.drawable.flag_samoa;
            case "xk": //kosovo
                return R.drawable.flag_kosovo;
            case "ye": //yemen
                return R.drawable.flag_yemen;
            case "yt": //mayotte
                return R.drawable.flag_martinique; // no exact flag found
            case "za": //south africa
                return R.drawable.flag_south_africa;
            case "zm": //zambia
                return R.drawable.flag_zambia;
            case "zw": //zimbabwe
                return R.drawable.flag_zimbabwe;
            default:
                return R.drawable.flag_transparent;
        }
    }

    private Country getFilter(String str) {


        for (Country country : this.countryList) {

            if (country.getCountryNameCode().toLowerCase().contains(str.toLowerCase())) {
                country.setCountryFlag(getFlagResID(country));
                return country;
            }
        }


        return null;
    }

    public String getCountryName() {
        return country.getCountryName();
    }

    public String getCountryNameCode() {
        return country.getCountryNameCode();
    }

    public String getCountryPhoneCode() {
        return country.getCountryPhoneCode();
    }

    public String getCountryPhoneCodeWithPlus() {
        return "+" + country.getCountryPhoneCode();
    }


    // PickerDialog click response =================
    @Override
    public void onItem(Country country) {
        setDefaultCountyNameCode(country.getCountryNameCode());
    }


    // CountryCodePicker click response in hear ====================
    private OnCountryStateChangeListener listener;

    public interface OnCountryStateChangeListener {
        void countryStateChanged(View v);
    }


    //  others class ==================
    public CountryPhoneCode CountryPhoneCode = new CountryPhoneCode();

    class CountryPhoneCode {

        public String toString() {
            String str = toStringWidthPlus();
            return str.contains("+") ? str.substring(1, str.length()) : str;
        }

        public String toStringWidthPlus() {
            return pickerPhoneCode.getText().toString().trim();
        }

        public int Integer() {
            return (int) Integer.parseInt(toString());
        }

        public float Float() {


            return (float) Float.parseFloat(toString());
        }

        public void setText(int code) {
            pickerPhoneCode.setText("+" + code);
        }


    }

    public CountryNameCode CountryNameCode = new CountryNameCode();

    class CountryNameCode {

        public String toString() {
            return pickerNameCode.getText().toString().trim();
        }

        public void setText(String resId) {
            pickerNameCode.setText(resId);
        }

    }

    public CountryFlag CountryFlag = new CountryFlag();

    class CountryFlag {

        public int getResourcesId() {
            return pickerFlag.getId();
        }


        public void setImageResource(int resId) {
            pickerFlag.setImageResource(resId);
        }

    }


}
