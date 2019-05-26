package nil.filmesseries;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFilmesSeries extends RecyclerView.Adapter<AdaptadorFilmesSeries.ViewHolderFS> {

    private Cursor cursor;
    private Context context;

    public AdaptadorFilmesSeries(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        if(this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolderFS} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolderFS, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolderFS, int)
     */
    @NonNull
    @Override
    public ViewHolderFS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemFS = LayoutInflater.from(context).inflate(R.layout.item_fs, parent, false);

        return new ViewHolderFS(itemFS);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolderFS#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolderFS#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolderFS, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderFS holder, int position) {
        cursor.moveToPosition(position);
        filmesSeries fs = filmesSeries.fromCursor(cursor);
        holder.setFilmeSerie(fs);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(cursor == null){
            return 0;
        }

        return cursor.getCount();
    }

    private static ViewHolderFS viewHolderFSSelecionado = null;

    public class ViewHolderFS extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewNome;
        private TextView textViewFormato;
        private TextView textViewEpisodios;

        private filmesSeries fs;

        public ViewHolderFS(@NonNull View itemView) {

            super(itemView);

            textViewNome = itemView.findViewById(R.id.textViewItem_fs_nome);
            textViewFormato = itemView.findViewById(R.id.textViewItem_fs_formato);
            textViewEpisodios = itemView.findViewById(R.id.textViewItem_fs_episodios);

            itemView.setOnClickListener(this);
        }

        public void setFilmeSerie(filmesSeries fs) {

            this.fs = fs;

            String ep = fs.getnEpiVistos() + "/" + fs.getnEpisodios();
            textViewNome.setText(fs.getNome());
            if(fs.getFormato() == 0){

                textViewFormato.setText(R.string.Serie);
            }else{

                textViewFormato.setText(R.string.Filme);
            }
            textViewEpisodios.setText(ep);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, EditarFSActivity.class);

            intent.putExtra("FS", fs);

            context.startActivity(intent);
        }
    }
}
