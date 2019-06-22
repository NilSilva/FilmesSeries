package nil.filmesseries;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorPessoas extends RecyclerView.Adapter<AdaptadorPessoas.ViewHolderP> {

    private String TAG = "AdaptadorPessoas";

    private Cursor cursor;
    private Context context;
    private filmesSeries fs;

    public AdaptadorPessoas(Context context) {

        this.context = context;
    }

    public AdaptadorPessoas(Context context, filmesSeries fs) {

        this.fs = fs;
        this.context = context;
    }

    public void setCursor(Cursor cursor) {

        if (this.cursor != cursor) {

            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolderP} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolderP, int)} . Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolderP, int)
     */
    @NonNull
    @Override
    public ViewHolderP onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemP;

        if (context instanceof FSDetalhesActivity) {

            itemP = LayoutInflater.from(context).inflate(R.layout.item_p_fs, parent, false);
        } else {

            itemP = LayoutInflater.from(context).inflate(R.layout.item_pessoa, parent, false);
        }

        return new ViewHolderP(itemP);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolderP#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolderP#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolderP, int)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderP holder, int position) {

        cursor.moveToPosition(position);
        Pessoas p = Pessoas.fromCursor(cursor);
        holder.setPessoa(p);
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        if (cursor == null) {

            return 0;
        }

        return cursor.getCount();
    }

    private static ViewHolderP viewHolderPSelecionado = null;

    public class ViewHolderP extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewNome;
        private TextView textViewFuncao;
        private ImageView imageViewP;

        private Pessoas p;

        public ViewHolderP(@NonNull View itemView) {

            super(itemView);

            textViewNome = itemView.findViewById(R.id.textViewItem_pessoa_nome);
            textViewFuncao = itemView.findViewById(R.id.textViewItem_pessoa_funcao);
            imageViewP = itemView.findViewById(R.id.imageViewItemP);

            itemView.setOnClickListener(this);
        }

        public void setPessoa(Pessoas p) {

            this.p = p;

            Bitmap bitmap = BitmapFactory.decodeByteArray(p.getImagem(), 0, p.getImagem().length);

            if (!(context instanceof FSDetalhesActivity)) {
                textViewFuncao.setText(p.getFuncao());
                textViewNome.setText(p.getNome());
            }
            imageViewP.setImageBitmap(bitmap);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            if(context instanceof LinkPtoFSActivity){

                //TODO: adicionar varios atores de uma so vez

                BdFsOpenHelper openHelper = new BdFsOpenHelper(context);
                SQLiteDatabase db = openHelper.getWritableDatabase();

                BdTable_FS_Pessoas tabela = new BdTable_FS_Pessoas(db);

                FS_Pessoas fs_p = new FS_Pessoas();

                fs_p.setID_P(p.getID());
                fs_p.setID_FS(fs.getID());

                tabela.insert(fs_p.getContentValues());

                Log.d(TAG, "filmes id: " + fs.getID());

                ((LinkPtoFSActivity) context).finish();
            }else if(context instanceof  UnlinkPFSActivity) {

                BdFsOpenHelper openHelper = new BdFsOpenHelper(context);
                SQLiteDatabase db = openHelper.getWritableDatabase();

                BdTable_FS_Pessoas tabela = new BdTable_FS_Pessoas(db);

                FS_Pessoas fs_p = new FS_Pessoas();

                fs_p.setID_P(p.getID());
                fs_p.setID_FS(fs.getID());

                tabela.delete(
                        BdTable_FS_Pessoas.CAMPO_ID_FS + "=? AND " + BdTable_FS_Pessoas.CAMPO_ID_PESSOAS + "=?",
                        new String[]{String.valueOf(fs.getID()), String.valueOf(p.getID())}
                );

                ((UnlinkPFSActivity) context).finish();
            }else{
                Intent intent = new Intent(context, EditPeopleActivity.class);
                intent.putExtra("P", p);

                context.startActivity(intent);
            }
        }

        private void seleciona() {
            itemView.setBackgroundResource(R.color.colorItemSelecionado);
        }

        private void desseleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }
    }
}
