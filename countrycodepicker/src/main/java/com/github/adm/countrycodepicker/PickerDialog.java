package com.github.adm.countrycodepicker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class PickerDialog extends Dialog {

    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    private OnItemClick onItemClick;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<Country> countryList = new ArrayList<>();

    public PickerDialog(@NonNull Context context, List<Country> countries) {
        super(context);
        mContext = context;
        countryList = countries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.picker_dialog);


        recyclerView = findViewById(R.id.pickerD_recyclerV);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RecyclerAdapter(countryList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Country country) {

                onItemClick.onItem(country);
                PickerDialog.super.cancel();
            }
        });


        TextInputLayout textIPSearchBox = findViewById(R.id.pickerD_textIL_search_box);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        };
        textIPSearchBox.getEditText().addTextChangedListener(textWatcher);



    }


    // search country and filters  ===================
    private void filter(String str) {

        List<Country> countryList = new ArrayList<>();

        for (Country country : this.countryList) {

            if (country.getCountryNameCode().toLowerCase().contains(str.toLowerCase())) {
                countryList.add(country);
            }
        }

        adapter.getFilterList(countryList);


    }


    // PickerDialog click response ===========================
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItem(Country country);
    }


    // extra class =============================================================
    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


        private OnItemClickListener listener;
        private List<Country> countryList = new ArrayList<>();

        public RecyclerAdapter(List<Country> countries) {
            this.countryList = countries;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.picker_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Country country = countryList.get(position);
            holder.imageVCountryFlag.setImageResource(getFlagResID(country));
            holder.textVCountryName.setText(country.getCountryName() + " (" + country.getCountryNameCode().toUpperCase() + ")");
            holder.textVCountryPhoneCode.setText("+" + country.getCountryPhoneCode());

        }


        @Override
        public int getItemCount() {
            return countryList.size();
        }

        // extra class =======================
        public class ViewHolder extends RecyclerView.ViewHolder implements CountryCodePicker.OnCountryStateChangeListener {


            private ImageView imageVCountryFlag;
            private TextView textVCountryName;
            private TextView textVCountryPhoneCode;
            private LinearLayout linearL;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                imageVCountryFlag = itemView.findViewById(R.id.pickerI_imageV_country_flag);
                textVCountryName = itemView.findViewById(R.id.pickerI_textV_countryName);
                textVCountryPhoneCode = itemView.findViewById(R.id.pickerI_textV_country_phone_code);
                linearL = itemView.findViewById(R.id.pickerI_linerL);


                imageVCountryFlag.setOnClickListener(this::countryStateChanged);
                textVCountryName.setOnClickListener(this::countryStateChanged);
                textVCountryPhoneCode.setOnClickListener(this::countryStateChanged);
                linearL.setOnClickListener(this::countryStateChanged);
//                linearL.setOnClickListener(this::countryStateChanged);


            }


            @Override
            public void countryStateChanged(View v) {

                int position = getAdapterPosition();

                Country country = countryList.get(position);
                country.setCountryFlag(getFlagResID(country));
                listener.onItemClick(country);
            }
        }


        // RecyclerAdapter click response ====================
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(Country country);
        }


        // get all method ========================
        private void getFilterList(List<Country> countries) {
            this.countryList = countries;
            notifyDataSetChanged();
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

    }


}
