package ddwucom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Diary> myDiaryList;
    private LayoutInflater layoutInflater;

    public DiaryAdapter(Context context, int layout, ArrayList<Diary> myDiaryList) {
        this.context = context;
        this.layout = layout;
        this.myDiaryList = myDiaryList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myDiaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return myDiaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myDiaryList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        if(getItemId(pos) == 1)
            imageView.setImageResource(R.mipmap.spain0);
        else if((getItemId(pos) == 2))
            imageView.setImageResource(R.mipmap.cheski);
        else if((getItemId(pos) == 3))
            imageView.setImageResource(R.mipmap.danuve);
        else if((getItemId(pos) == 4))
            imageView.setImageResource(R.mipmap.halsutate);
        else
            imageView.setImageResource(R.mipmap.ic_launcher);



        TextView textNation = convertView.findViewById(R.id.textViewNation);
        TextView textPlace = convertView.findViewById(R.id.textViewPlace);
        TextView textTime = convertView.findViewById(R.id.textViewTime);

        textNation.setText(myDiaryList.get(pos).getNation());
        textPlace.setText(myDiaryList.get(pos).getPlace());
        textTime.setText(myDiaryList.get(pos).getDate());

        return  convertView;
    }
}
