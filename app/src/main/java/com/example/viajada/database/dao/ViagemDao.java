package com.example.viajada.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.viajada.database.DBOpenHelper;
import com.example.viajada.database.model.ViagemCustoAdicionalModel;
import com.example.viajada.database.model.ViagemModel;

import java.util.ArrayList;

public class ViagemDao extends BaseDao {

    private String[] colunas = {
            ViagemModel.COLUNA_ID,
            ViagemModel.COLUNA_USUARIO_ID,
            ViagemModel.COLUNA_PRINCIPAL_ORIGEM,
            ViagemModel.COLUNA_PRINCIPAL_DESTINO,
            ViagemModel.COLUNA_PRINCIPAL_DURACAO_DIAS,
            ViagemModel.COLUNA_PRINCIPAL_NUM_VIAJANTES,
            ViagemModel.COLUNA_COMBUSTIVEL_DISTANCIA_TOTAL_KM,
            ViagemModel.COLUNA_COMBUSTIVEL_MEDIA_KM_LITRO,
            ViagemModel.COLUNA_COMBUSTIVEL_CUSTO_MEDIO_LITRO,
            ViagemModel.COLUNA_COMBUSTIVEL_NUM_VEICULOS,
            ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA,
            ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO,
            ViagemModel.COLUNA_REFEICAO_CUSTO_MEDIO,
            ViagemModel.COLUNA_REFEICAO_POR_DIA,
            ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE,
            ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES
    };

    private ViagemCustoAdicionalDao custoAdicionalDao;

    public ViagemDao(Context context) {
        db_helper = new DBOpenHelper(context);
        custoAdicionalDao = new ViagemCustoAdicionalDao(context);
    }

    public long Inserir(ViagemModel model) {

        long rowId = -1;

        try {
            Open();

            //colunas obrigatorias
            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_USUARIO_ID, model.getUsuarioId());
            values.put(ViagemModel.COLUNA_PRINCIPAL_ORIGEM, model.getPrincipalOrigem());
            values.put(ViagemModel.COLUNA_PRINCIPAL_DESTINO, model.getPrincipalDestino());
            values.put(ViagemModel.COLUNA_PRINCIPAL_DURACAO_DIAS, model.getPrincipalDuracaoDias());
            values.put(ViagemModel.COLUNA_PRINCIPAL_NUM_VIAJANTES, model.getPrincipalNumViajantes());
            values.put(ViagemModel.COLUNA_COMBUSTIVEL_DISTANCIA_TOTAL_KM, model.getCombustivelDistanciaTotalKm());
            values.put(ViagemModel.COLUNA_COMBUSTIVEL_MEDIA_KM_LITRO, model.getCombustivelMediaKmLitro());
            values.put(ViagemModel.COLUNA_COMBUSTIVEL_CUSTO_MEDIO_LITRO, model.getCombustivelCustoMedioLitro());
            values.put(ViagemModel.COLUNA_COMBUSTIVEL_NUM_VEICULOS, model.getCombustivelNumVeiculos());
            values.put(ViagemModel.COLUNA_REFEICAO_CUSTO_MEDIO, model.getRefeicaoCustoMedio());
            values.put(ViagemModel.COLUNA_REFEICAO_POR_DIA, model.getRefeicaoPorDia());

            //se tem tarifa aerea
            if (model.getTarifaAereaCustoPessoa() != null && model.getTarifaAereaCustoAluguelVeiculo() != null) {
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA, model.getTarifaAereaCustoPessoa());
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO, model.getTarifaAereaCustoAluguelVeiculo());
            }

            //se tem hospedagem
            if (model.getHospedagemCustoMedioNoite() != null && model.getHospedagemTotalNoites() != null) {
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE, model.getHospedagemCustoMedioNoite());
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES, model.getHospedagemTotalNoites());
            }

            //se tem custos adicionais
            if(!model.getCustosAdicionais().isEmpty()){
                custoAdicionalDao.Inserir(model.getCustosAdicionais(), model.getId());
            }

            rowId = db.insert(ViagemModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }

        return rowId;
    }

    public long DeletarPorId(long id) {

        long rowId = -1;

        try {
            custoAdicionalDao.DeletarPorViagemId(id);

            Open();
            // delete from tb_viagem where _id = 12345;
            rowId = db.delete
                    (
                            ViagemModel.TABELA_NOME,
                            ViagemModel.COLUNA_ID + " = ?",
                            new String[]{String.valueOf(id)}
                    );
        } finally {
            Close();
        }

        return rowId;
    }

    public ArrayList<ViagemModel> ListarTodas() {

        Open();

        ArrayList<ViagemModel> lista = new ArrayList<ViagemModel>();

        // select * from tb_produtos;
        Cursor c = db.query
                (
                        ViagemModel.TABELA_NOME,
                        colunas,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        c.moveToFirst();

        while (!c.isAfterLast()) {
            ViagemModel model = new ViagemModel();

            model.setId(c.getInt(0));
            model.setUsuarioId(c.getInt(1));

            model.setPrincipalOrigem(c.getString(2));
            model.setPrincipalDestino(c.getString(3));
            model.setPrincipalDuracaoDias(c.getInt(4));
            model.setPrincipalNumViajantes(c.getInt(5));

            model.setCombustivelDistanciaTotalKm(c.getInt(6));
            model.setCombustivelMediaKmLitro(c.getFloat(7));
            model.setCombustivelCustoMedioLitro(c.getFloat(8));
            model.setCombustivelNumVeiculos(c.getInt(9));

            model.setTarifaAereaCustoPessoa(c.getFloat(10));
            model.setTarifaAereaCustoAluguelVeiculo(c.getFloat(11));

            model.setRefeicaoCustoMedio(c.getFloat(12));
            model.setRefeicaoPorDia(c.getInt(13));

            model.setHospedagemCustoMedioNoite(c.getFloat(14));
            model.setHospedagemTotalNoites(c.getInt(15));

            c.moveToNext();
        }

        Close();
        return lista;
    }

    public ViagemModel ConsultarPorId(long id) {
        ArrayList<ViagemCustoAdicionalModel> custosAdicionais = custoAdicionalDao.ConsultarPorViagemId(id);

        Open();

        // select * from tb_produtos;
        Cursor c = db.query
                (
                        ViagemModel.TABELA_NOME,
                        colunas,
                        ViagemModel.COLUNA_ID + " = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null
                );

        c.moveToFirst();

        ViagemModel model = new ViagemModel();

        model.setId(c.getInt(0));
        model.setUsuarioId(c.getInt(1));

        model.setPrincipalOrigem(c.getString(2));
        model.setPrincipalDestino(c.getString(3));
        model.setPrincipalDuracaoDias(c.getInt(4));
        model.setPrincipalNumViajantes(c.getInt(5));

        model.setCombustivelDistanciaTotalKm(c.getInt(6));
        model.setCombustivelMediaKmLitro(c.getFloat(7));
        model.setCombustivelCustoMedioLitro(c.getFloat(8));
        model.setCombustivelNumVeiculos(c.getInt(9));

        model.setTarifaAereaCustoPessoa(c.getFloat(10));
        model.setTarifaAereaCustoAluguelVeiculo(c.getFloat(11));

        model.setRefeicaoCustoMedio(c.getFloat(12));
        model.setRefeicaoPorDia(c.getInt(13));

        model.setHospedagemCustoMedioNoite(c.getFloat(14));
        model.setHospedagemTotalNoites(c.getInt(15));

        model.setCustosAdicionais(custosAdicionais);

        Close();

        return model;
    }

}
