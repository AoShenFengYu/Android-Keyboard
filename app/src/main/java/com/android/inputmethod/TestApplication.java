package com.android.inputmethod;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDexApplication;

import com.airbnb.lottie.LottieCompositionFactory;
import com.android.inputmethod.latin.R;
import com.nlptech.Agent;
import com.nlptech.common.utils.DensityUtil;
import com.nlptech.function.keyboardrender.RGBKeyboardRender;
import com.nlptech.function.theme.download_theme.DownloadThemeDataFetcher;
import com.nlptech.keyboardview.theme.download.DownloadThemeManager;
import com.nlptech.keyboardview.theme.external.ExternalThemeInfo;

import io.reactivex.plugins.RxJavaPlugins;


public class TestApplication extends MultiDexApplication {

    private static TestApplication instance;

    public static TestApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setRxJavaErrorHandler();
        Agent.getInstance().init(this);
        addExternalThemeDefault();
        addExternalThemeRBG();
        Agent.getInstance().loadTheme(this, "001");
        DownloadThemeManager.getInstance().setDownloadThemeDataListener(new DownloadThemeDataFetcher());
        DownloadThemeManager.getInstance().triggerFetchData(this);
    }

    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> Log.e("vertex", "RxJavaError: " + throwable));
    }

    private void addExternalThemeDefault() {

        Drawable keyboardBackgroundDrawable = ContextCompat.getDrawable(this, R.drawable.test_background_lxx_light);
        Drawable morekeysBackgroundDrawable = ContextCompat.getDrawable(this, R.drawable.test_theme_more_keyboard_background);
        Drawable keyPreviewDrwable = ContextCompat.getDrawable(this, R.drawable.test_theme_preview);
        Drawable emojiIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_smiley);
        Drawable deleteIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_delete);
        Drawable shiftIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_shift);
        Drawable shiftLockIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_shift_locked);
        Drawable enterIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_enter);
        Drawable languageIcon = ContextCompat.getDrawable(this, R.drawable.test_icon_language);
        Drawable chineseSuggestionMorePageBackground = new ColorDrawable(Color.parseColor("#3c3c3c"));
        Drawable chineseSuggestionComposingViewBackground = new ColorDrawable(Color.parseColor("#AA000000"));
        Drawable suggestionStripViewBackground = new ColorDrawable(Color.BLACK);

        StateListDrawable keyBackgroundDrawable = new StateListDrawable();
        keyBackgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(this, R.drawable.test_theme_key_press));
        keyBackgroundDrawable.addState(new int[]{}, ContextCompat.getDrawable(this, R.drawable.test_theme_key_normal));

        StateListDrawable functionKeyBackgroundDrawable = new StateListDrawable();
        functionKeyBackgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(this, R.drawable.test_theme_key_press));
        functionKeyBackgroundDrawable.addState(new int[]{}, ContextCompat.getDrawable(this, R.drawable.test_theme_function_key_normal));

        StateListDrawable morekeyDrawable = new StateListDrawable();
        morekeyDrawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(this, R.drawable.test_theme_more_key_press));
        morekeyDrawable.addState(new int[]{}, ContextCompat.getDrawable(this, R.drawable.test_theme_key_normal));

        StateListDrawable spacebarBackgroundDrawable = new StateListDrawable();
        spacebarBackgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, ContextCompat.getDrawable(this, R.drawable.test_theme_space_key_press));
        spacebarBackgroundDrawable.addState(new int[]{}, ContextCompat.getDrawable(this, R.drawable.test_theme_space_key_normal));

        ExternalThemeInfo.LottieDrawableInfo lottieDrawableInfo = new ExternalThemeInfo.LottieDrawableInfo(() -> LottieCompositionFactory
                .fromAsset(this, "test_lottie_click_effect.json"), 1);

        int dividerW = DensityUtil.dp2px(this, 1);
        int dividerH = DensityUtil.dp2px(this, 20);
        Bitmap bitmap = Bitmap.createBitmap(dividerW, dividerH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#12f0e5"));
        canvas.drawRect(new Rect(0, 0, dividerW, dividerH), paint);
        Drawable suggestDivider = new BitmapDrawable(getResources(), bitmap);
        String color = String.format("#%06X", 0xFFFFFF & 0x12f0e5);
        String emojiColor = String.format("#%06X", 0xFFFFFF & 0x048f89);
        ExternalThemeInfo externalThemeInfo = new ExternalThemeInfo.Builder(TestApplication.getInstance(), "001", "Test Theme")
                .setKeyboardBackground(keyboardBackgroundDrawable)
                .setKeyBackground(keyBackgroundDrawable)
                .setFunctionKeyBackground(functionKeyBackgroundDrawable)
                .setSpacebarBackground(spacebarBackgroundDrawable)
                .setKeyPreviewBackground(keyPreviewDrwable)
                .setKeyLetterSizeRatio(0.4f)
                .setKeyTextColor(color)
                .setSearchKeyIcon(ContextCompat.getDrawable(this, R.drawable.test_icon_search))
                .setNextKeyIcon(ContextCompat.getDrawable(this, R.drawable.test_icon_next))
                .setPreviousKeyIcon(ContextCompat.getDrawable(this, R.drawable.test_icon_previous))
                .setKeyHintLetterColor(color)
                .setFunctionKeyTextColor(color)
                .setMoreKeysKeyboardBackground(morekeysBackgroundDrawable)
                .setMoreKeysKeyBackground(morekeyDrawable)
                .setKeyPreviewTextColor(color)
                .setGestureTrailColor(color)
                .setEmojiNormalKeyIcon(emojiIcon)
                .setEmojiActionKeyIcon(emojiIcon)
                .setDeleteKeyIcon(deleteIcon)
                .setShiftKeyIcon(shiftIcon)
                .setShiftKeyShiftedIcon(shiftLockIcon)
                .setEnterKeyIcon(enterIcon)
                .setLanguageSwitchKeyIcon(languageIcon)
                .setEmojiCategoryPageIndicatorBackgroundColor(emojiColor)
                .setEmojiCategoryPageIndicatorForegroundColor(emojiColor)
                .setEmojiSeparatorColor(emojiColor)
                .setUiIconColor(color)
                .setLanguageOnSpacebarTextColor(color)
                .setSuggestedAutoCorrectColor(color)
                .setSuggestedTextColor(color)
                .setSuggestedTypedWordColor(color)
                .setSuggestedValidTypedWordColor(color)
                .setSuggestionStripDivider(suggestDivider)
                .setSuggestionStripViewBackground(suggestionStripViewBackground)
                .setKeyboardClickedEffectLottieDrawable(lottieDrawableInfo)
                .setThemePreviewImage(ContextCompat.getDrawable(this, R.drawable.test_thumbnail))
                .setChineseSuggestionMorePageBackground(chineseSuggestionMorePageBackground)
                .setChineseSuggestionStripOpenMorePageButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_open_moepage))
                .setChineseSuggestionStripCloseMorePageButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_close_moepage))
                .setChineseSuggestionComposingViewBackground(chineseSuggestionComposingViewBackground)
                .setChineseSuggestionComposingTextColor("#009393")
                .setChineseSuggestionMorePageUpEnableButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_up_arrow_enable))
                .setChineseSuggestionMorePageUpDisableButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_up_arrow_disable))
                .setChineseSuggestionMorePageDownEnableButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_down_arrow_enable))
                .setChineseSuggestionMorePageDownDisableButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_down_arrow_disable))
                .setChineseSuggestionMorePageDeleteButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_delete))
                .setChineseSuggestionMorePageResetButton(ContextCompat.getDrawable(this, R.drawable.ic_external_theme_rgb_cn_suggest_retransfusion))
                .build();
        Agent.getInstance().addExternalThemes(this, externalThemeInfo);
    }

    private void addExternalThemeRBG() {
        ExternalThemeInfo.Builder builder = new ExternalThemeInfo.Builder(this, "external_theme_id_rgb", "RGB");
        builder.setThemePreviewImage(R.drawable.img_external_theme_preview_rgb);
        builder.setThemePreviewImageWithBorder(R.drawable.img_external_theme_preview_rgb_with_border);

        ColorDrawable colorDrawable;
        String color;

        // keyboard
        colorDrawable = new ColorDrawable(Color.BLACK);
        builder.setKeyboardBackground(colorDrawable);
        colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        builder.setKeyBackground(colorDrawable, colorDrawable);
        colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        builder.setFunctionKeyBackground(colorDrawable, colorDrawable);

        builder.setSpacebarBackground(R.drawable.bg_external_theme_rgb_spacebar, R.drawable.bg_external_theme_rgb_spacebar);

        colorDrawable = new ColorDrawable(Color.parseColor("#1c2935"));
        builder.setKeyPreviewBackground(colorDrawable);
        color = String.format("#%06X", 0xFFFFFF & Color.TRANSPARENT);
        builder.setEmojiCategoryPageIndicatorBackgroundColor(color);

        // more keys keyboard
        colorDrawable = new ColorDrawable(Color.parseColor("#000000"));
        builder.setMoreKeysKeyboardBackground(colorDrawable);
        builder.setMoreKeysKeyBackground(R.drawable.bg_external_theme_rgb_more_key, R.drawable.bg_external_theme_rgb_more_key);

        // Chinese Suggest MorePage
        colorDrawable = new ColorDrawable(Color.parseColor("#3c3c3c"));
        builder.setChineseSuggestionMorePageBackground(colorDrawable);
        builder.setChineseSuggestionStripOpenMorePageButton(R.drawable.ic_external_theme_rgb_cn_open_moepage);
        builder.setChineseSuggestionStripCloseMorePageButton(R.drawable.ic_external_theme_rgb_cn_close_moepage);
        colorDrawable = new ColorDrawable(Color.parseColor("#AA000000"));
        builder.setChineseSuggestionComposingViewBackground(colorDrawable);
        builder.setChineseSuggestionComposingTextColor("#ff5151");
        builder.setChineseSuggestionMorePageUpEnableButton(R.drawable.ic_external_theme_rgb_cn_suggest_up_arrow_enable);
        builder.setChineseSuggestionMorePageUpDisableButton(R.drawable.ic_external_theme_rgb_cn_suggest_up_arrow_disable);
        builder.setChineseSuggestionMorePageDownEnableButton(R.drawable.ic_external_theme_rgb_cn_suggest_down_arrow_enable);
        builder.setChineseSuggestionMorePageDownDisableButton(R.drawable.ic_external_theme_rgb_cn_suggest_down_arrow_disable);
        builder.setChineseSuggestionMorePageDeleteButton(R.drawable.ic_external_theme_rgb_cn_suggest_delete);
        builder.setChineseSuggestionMorePageResetButton(R.drawable.ic_external_theme_rgb_cn_suggest_retransfusion);

        // color
        color = String.format("#%06X", 0xFFFFFF & Color.WHITE);
        builder.setKeyTextColor(color);
        builder.setFunctionKeyTextColor(color);
        builder.setLanguageOnSpacebarTextColor(color);
        builder.setKeyHintLabelColor(color);
        builder.setKeyHintLetterColor(color);
        builder.setKeyTextInactivatedColor(color);
        builder.setKeyShiftedLetterHintActivatedColor(color);
        builder.setKeyShiftedLetterHintInactivatedColor(color);
        builder.setKeyPreviewTextColor(color);
        builder.setEmojiCategoryPageIndicatorForegroundColor(color);
        builder.setEmojiCategoryPageIndicatorBackgroundColor(color);
        builder.setCreateKeyboardRenderCallback(RGBKeyboardRender::new);
        builder.setUiIconColor("#c5c5c5");
        builder.setUiFunctionEntryIconColor("#c5c5c5");

        ColorDrawable suggestColor = new ColorDrawable(Color.parseColor("#000000"));
        builder.setSuggestedTextColor("#ffffff");
        builder.setSuggestedAutoCorrectColor("#ffffff");
        builder.setSuggestionStripViewBackground(suggestColor);
        builder.setSuggestedValidTypedWordColor("#ffffff");
        builder.setSuggestedTypedWordColor("#ffffff");

        // add theme
        builder.setBorderMode(ExternalThemeInfo.Builder.BORDER_MODE_BOTH);
        ExternalThemeInfo info = builder.build();
        Agent.getInstance().addExternalThemes(this, info);
    }

}
