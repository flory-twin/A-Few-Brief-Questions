package com.invaliddomain.myfirstproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DateTimeQuestionViewGroup extends ViewGroup {
    //Top-level view
    // LinearLayour(vertical)
    //8 DP padding
    // Question text, wrapped to screen width
    //Button "Now", DT box which allows entry
    private LinearLayout questionAnswerLayout;
    private Context context;
    private TextView questionLabel;
    private EditText dtLabel;
    private Button nowButton;
    public DateTimeQuestion dtQuestion;
    private AlertDialog confirmationDialogue;
    private View.OnClickListener onNowButtonClickListener;
    private View.OnClickListener confirmationButtonYes;
    private View.OnClickListener dateTimeEntryFieldPopupOnCompleteListener;
    private View.OnClickListener dateTimeOnClickListener;

    public DateTimeQuestionViewGroup(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.date_time_question_layout, this, true);

        int[] attributes = {R.styleable.DateTimeQuestionViewGroup_questionText};
        TypedArray a = context.obtainStyledAttributes(attrSet,
                attributes);
        String questionText = a.getString(R.styleable.DateTimeQuestionViewGroup_questionText);
        a.recycle();

        this.dtQuestion = new DateTimeQuestion(questionText);
        this.initalizeLayout();
    }

    private void initalizeLayout()
    {
        //Set up question text.
        questionLabel = (TextView) this.getChildAt(0);
        questionLabel.setText(this.dtQuestion.getQuestionAsText());
        questionAnswerLayout.addView(questionLabel);
        //Add "Now" button.
        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        nowButton = new Button(context);
        nowButton.setText(R.string.now);
        this.initializeNowButtonListener();
        nowButton.setOnClickListener(onNowButtonClickListener);
        buttonsLayout.addView(nowButton);
        //Add time label.
        dtLabel = new EditText(this.context);
        dtLabel.setText("");
        buttonsLayout.addView(dtLabel);
        //Pack the whole thing up.
        questionAnswerLayout.addView(buttonsLayout);

        questionAnswerLayout.setId(View.generateViewId());
    }


    private void displayNowConfirmationDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.confirmDateTimeUpdateMessage)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dtQuestion.setAnswerToNow();
                        dialog.dismiss();
                        repaint();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog; no actions.
                        dialog.dismiss();
                    }
                });
    }

    private void initializeNowButtonListener() {
        onNowButtonClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                dtQuestion.setAnswerToNow();
                initializeTimeDisplay();
                /*
                //If there's no date just yet, just do the update.
                if (!this.getAnswerAsLocalDateTime() instanceof LocalDateTime ||
                        this.() == null) {
                    dtQuestion.setAnswerToNow();
                    repaint();
                }
                //Otherwise, bring up the confirmation dialogue.
                else
                {
                    displayNowConfirmationDialogue();
                }
                */
            }
        };
    }
    /*
    public void initializeDateTimeOnClickListener()
    {
        dateTimeOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (this.dtQuestion.getAnswerAsLocalDateTime() instanceof LocalDateTime &&
                        this.getAnswerAsLocalDateTime() != null) {
                    TimePickerDialog timeDialog = new TimePickerDialog(
                            this.getContext(),
                            this,
                            dtQuestion.answerAsDate.getHour(),
                            dtQuestion.answerAsDate.getMinute(),
                            true) {
                        @Override
                        public LocalDateTime onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                            super.onTimeChanged(view, hourOfDay, minute);
                            return new LocalDateTime(dtQuestion)
                        }
                    }
                }
                //Otherwise, bring up the confirmation dialogue.
                else
                {

                }
                //Throw up a D/T entry dialogue.
                new TimePickerDialog(getActivity(), this, hour, minute,
                            DateFormat.is24HourFormat(getActivity()));
                }

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // Do something with the time chosen by the user
                }

            }
        }
    }
*/
    private void initializeTimeDisplay()
    {
        dtLabel.setText(this.dtQuestion.getAnswerAsText());
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onDraw(Canvas c)
    {

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    public void repaint()
    {

    }
    //Repainting, onDraw, invalidate, ViewConfiguration, "canvas api"
    //onMeasure, setMeasuredDimension()
    //ViewGroup, onMeasure, onLayout, ViewGroup.LayoutParams
    //onAttachedToWindow, onDetachedFromWindow


}
