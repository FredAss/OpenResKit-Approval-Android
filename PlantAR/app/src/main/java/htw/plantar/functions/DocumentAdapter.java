package htw.plantar.functions;

import java.util.List;

import com.example.plantar.R;

import htw.plantar.models.Document;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DocumentAdapter extends ArrayAdapter<Document>
{
	@SuppressWarnings("unused")
	private List<Document> m_Documennts;
	
	public DocumentAdapter(Context context, int resource, List<Document> objects) 
	{
		super(context, resource, objects);
		m_Documennts = objects;
	}
	
	private class ViewHolder
	{
		TextView m_ItemDocumentTypeTextView;
		TextView m_ItemDocumentNameTextView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		final ViewHolder viewHolder;
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.document_item_row, null);
			
			viewHolder = new ViewHolder();
			viewHolder.m_ItemDocumentTypeTextView = (TextView)convertView.findViewById(R.id.item_document_type);
			viewHolder.m_ItemDocumentNameTextView = (TextView)convertView.findViewById(R.id.item_document_name);
		
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.item_document_type, viewHolder.m_ItemDocumentTypeTextView);
			convertView.setTag(R.id.item_document_name, viewHolder.m_ItemDocumentNameTextView);
		}
		else
			viewHolder = (ViewHolder)convertView.getTag();
		
		Document currentDocument = getItem(position);

		viewHolder.m_ItemDocumentTypeTextView.setTag(position);
		viewHolder.m_ItemDocumentNameTextView.setTag(position);
		
		if(currentDocument.getName() != null)
		{
			String[] nameAndType = currentDocument.getName().split("\\.");
			
			viewHolder.m_ItemDocumentTypeTextView.setText("." + (nameAndType[1].toUpperCase()));
			viewHolder.m_ItemDocumentNameTextView.setText(nameAndType[0]);
		}

		return convertView;
	}
}
