package com.invaliddomain.myfirstproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeQuestionLayout extends ConstraintLayout {
    //Top-level view
    // LinearLayour(vertical)
    //8 DP padding
    // Question text, wrapped to screen width
    //Button "Now", DT box which allows entry
    public DateTimeQuestion dtQuestion;
    private Context context;
    private TextView questionLabel;
    private Button nowButton;
    private EditText dtLabel;
    private AlertDialog confirmationDialogue;

    public DateTimeQuestionLayout(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        this.context = context;

        this.setId(View.generateViewId());

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.date_time_question_layout, this, true);

        int[] attributes = {R.styleable.DateTimeQuestionLayout_questionText};
        TypedArray a = context.obtainStyledAttributes(attrSet,
                attributes);
        String questionText = a.getString(R.styleable.DateTimeQuestionLayout_questionText);
        a.recycle();

        this.dtQuestion = new DateTimeQuestion(questionText);
        this.initalizeConstraintLayout();
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private void initalizeConstraintLayout()
    {
        //Set up question text.
        this.initializeQuestionLabel();

        //Set up "Now" button.
        this.initializeNowButton();

        //Add time label.
        this.initializeTimeDisplay();

        //Pack the whole thing up.
        this.initializeConstraints();
        this.repaint();
    }
    /*
     * --------------------------------------------------
     * Main sub-view initializers.
     * --------------------------------------------------
     */
    private void initializeQuestionLabel()
    {
        questionLabel = (TextView) this.getChildAt(0);
        questionLabel.setId(View.generateViewId());
        questionLabel.setText(this.dtQuestion.getQuestionAsText());
    }
    private void initializeNowButton()
    {
        nowButton = (Button) this.getChildAt(1);
        nowButton.setId(View.generateViewId());
        nowButton.setText(R.string.now);
        this.initializeNowButtonLogic();
    }
    private void initializeTimeDisplay()
    {
        dtLabel = (EditText) this.getChildAt(2);
        dtLabel.setId(View.generateViewId());
        dtLabel.setText(this.dtQuestion.getAnswerAsText());
        initializeDateTimeOnClickListener();
    }
    /*
     * --------------------------------------------------
     * Helpers.
     * --------------------------------------------------
     */
    private void initializeNowButtonLogic() {
        OnClickListener onNowButtonClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //If we're initializing, and there's no time there yet, just overwrite the button.
                if (!(dtQuestion.getAnswerAsDate() instanceof Date) ||
                        dtQuestion.getAnswerAsDate() == null ||
                        dtQuestion.getAnswerAsText() == "    -  -     :  :  "){
                    dtQuestion.setAnswerToNow();
                    repaintDTLabel();
                }
                //Otherwise, bring up the confirmation dialogue.
                else
                {
                    displayNowConfirmationDialogue();
                }

            }
        };
        nowButton.setOnClickListener(onNowButtonClickListener);
    }

    private void displayNowConfirmationDialogue() {
        final Date oldDate = dtQuestion.getAnswerAsDate();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage(R.string.confirmDateTimeUpdateMessage)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dtQuestion.setAnswerToNow();
                        repaintDTLabel();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog; no actions.
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void initializeDateTimeOnClickListener() {
        OnClickListener dateTimeOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeDialog = new TimePickerDialog(
                        context,
                        null,
                        dtQuestion.getAnswerAsDate().getHours(),
                        dtQuestion.getAnswerAsDate().getMinutes(),
                        true) {
                    @Override
                    public void onTimeChanged(TimePicker view, final int hourOfDay, final int minute) {
                        super.onTimeChanged(view, hourOfDay, minute);
                        final Date oldDate = dtQuestion.getAnswerAsDate();
                        //Display a confirmation dialogue.
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                        builder.setMessage(R.string.confirmDateTimeUpdateMessage)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dtQuestion.setAnswerToNow();
                                        repaintDTLabel();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog; no actions.
                                        dialog.dismiss();
                                    }
                                });
                        this.dismiss();
                        repaint();
                    }

                };
            }
        };
    }

/*    @Override
    protected void onLayout(boolean changed,
        int left,
        int top,
        int right,
        int bottom)*/

    protected void initializeConstraints()
    {
        //Get the initial layout information.
        //ConstraintLayout initialLayout = (ConstraintLayout) getViewById(R.layout.date_time_question_layout);
        ConstraintSet constraints = new ConstraintSet();
        constraints.clone(this);

        int nowButtonHeight = nowButton.getMeasuredHeight() +
                nowButton.getPaddingTop() + nowButton.getPaddingBottom();
        int dtLabelHeight = dtLabel.getMeasuredHeight() +
                dtLabel.getPaddingTop() + dtLabel.getPaddingBottom();
        int measuredHeight =
                questionLabel.getMeasuredHeight() +
                        ( nowButtonHeight > dtLabelHeight ?
                                nowButtonHeight :
                                dtLabelHeight);
        constraints.constrainMinHeight(this.getId(), measuredHeight);
        constraints.connect(
                this.getId(),
                ConstraintSet.TOP,
                questionLabel.getId(),
                ConstraintSet.TOP,
                questionLabel.getPaddingTop());

        constraints.connect(
                nowButton.getId(),
                ConstraintSet.TOP,
                questionLabel.getId(),
                ConstraintSet.BOTTOM,
                questionLabel.getPaddingBottom());

        constraints.connect(
                dtLabel.getId(),
                ConstraintSet.LEFT,
                nowButton.getId(),
                ConstraintSet.RIGHT,
                nowButton.getPaddingRight());

        constraints.connect(
                dtLabel.getId(),
                ConstraintSet.TOP,
                questionLabel.getId(),
                ConstraintSet.BOTTOM,
                questionLabel.getPaddingBottom());
        /*
        //Connect topmost label.

        constraints.connect(
                questionLabel.getId(),
                ConstraintSet.LEFT,
                this.getId(),
                ConstraintSet.LEFT,
                questionLabel.getPaddingBottom());
        constraints.connect(
                questionLabel.getId(),
                ConstraintSet.RIGHT,
                this.getId(),
                ConstraintSet.RIGHT,
                questionLabel.getPaddingRight());




        constraints.connect(
                nowButton.getId(),
                ConstraintSet.LEFT,
                this.getId(),
                ConstraintSet.LEFT);
        constraints.connect(
                dtLabel.getId(),
                ConstraintSet.LEFT,
                this.getId(),
                ConstraintSet.RIGHT);

        */
        constraints.applyTo(this);

    }

    public void repaint()
    {
        dtLabel.invalidate();
    }

    public void repaintDTLabel()
    {
        dtLabel.setText(this.dtQuestion.getAnswerAsText());
        dtLabel.invalidate();
    }
    //Repainting, onDraw, invalidate, ViewConfiguration, "canvas api"
    //onMeasure, setMeasuredDimension()
    //ViewGroup, onMeasure, onLayout, ViewGroup.LayoutParams
    //onAttachedToWindow, onDetachedFromWindow

    /*
    @Override
    protected void onMeasure(int parentWidthMeasureSpec, int parentHeightMeasureSpec)
    {

        //getSuggestedMinimumWidth(), getSuggestedMinimumHeight
        //MeasureSpec: UNSPECIFIED, EXACTLY, AT_MOST

        //Tell the two contributors to the width to work out their measurements.
        //resolveSize(int size, MeasureSpec.)
        int widthPaddingLowerRow = nowButton.getPaddingLeft() + nowButton.getPaddingRight() +
                dtLabel.getPaddingLeft() + dtLabel.getPaddingRight();

        //int parentWidthMode = MeasureSpec.getMode(parentWidthMeasureSpec);
        int parentWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        //int parentHeightMode = MeasureSpec.getMode(parentWidthMeasureSpec);
        //int parentHeightSize = MeasureSpec.getSize(parentWidthMeasureSpec);

        int nowButtonHeight = nowButton.getMeasuredHeight() +
                nowButton.getPaddingTop() + nowButton.getPaddingBottom();
        int dtLabelHeight = dtLabel.getMeasuredHeight() +
                dtLabel.getPaddingTop() + dtLabel.getPaddingBottom();
        int measuredHeight =
                questionLabel.getMeasuredHeight() +
                        ( nowButtonHeight > dtLabelHeight ?
                            nowButtonHeight :
                            dtLabelHeight);

        //setMeasuredDimension(parentWidth, measuredHeight);
        setMeasuredDimension(300, 300);
    }
    */

    /*
    protected void onMeasureWidth(int parentWidthMeasureSpec)
    {
        //Get the parent's mode and specified size.
        int parentMode = MeasureSpec.getMode(parentWidthMeasureSpec);
        int parentSize = MeasureSpec.getSize(parentWidthMeasureSpec);
        if (parentMode == MeasureSpec.EXACTLY || parentMode == MeasureSpec.AT_MOST)
        {

        }
        else if (parentMode == MeasureSpec.UNSPECIFIED)
        {
            //Return the max of the wrapped sizes.
        }
    }
    */
}
