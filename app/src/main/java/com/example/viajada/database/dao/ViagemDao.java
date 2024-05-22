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

            ContentValues values = new ContentValues();

            //colunas obrigatorias
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
            if (model.possuiPassagemAerea()) {
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA, model.getTarifaAereaCustoPessoa());
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO, model.getTarifaAereaCustoAluguelVeiculo());
            }

            //se tem hospedagem
            if (model.possuiHospedagem()) {
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE, model.getHospedagemCustoMedioNoite());
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES, model.getHospedagemTotalNoites());
            }

            rowId = db.insert(ViagemModel.TABELA_NOME, null, values);

        } finally {
            Close();
        }

        //se tem custos adicionais
        if (!model.getCustosAdicionais().isEmpty()) {
            custoAdicionalDao.Inserir(model.getCustosAdicionais(), rowId);
        }

        return rowId;
    }

    public void Atualizar(ViagemModel model){
        try {
            Open();

            ContentValues values = new ContentValues();

            //colunas obrigatorias
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
            if (model.possuiPassagemAerea()) {
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA, model.getTarifaAereaCustoPessoa());
                values.put(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO, model.getTarifaAereaCustoAluguelVeiculo());
            } else {
                values.putNull(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_PESSOA);
                values.putNull(ViagemModel.COLUNA_TARIFA_AEREA_CUSTO_ALUGUEL_VEICULO);
            }

            //se tem hospedagem
            if (model.possuiHospedagem()) {
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE, model.getHospedagemCustoMedioNoite());
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES, model.getHospedagemTotalNoites());
                values.put(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_QUARTOS, model.getHospedagemTotalQuartos());
            } else {
                values.putNull(ViagemModel.COLUNA_HOSPEDAGEM_CUSTO_MEDIO_NOITE);
                values.putNull(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_NOITES);
                values.putNull(ViagemModel.COLUNA_HOSPEDAGEM_TOTAL_QUARTOS);
            }

            db.update(ViagemModel.TABELA_NOME, values, ViagemModel.COLUNA_ID + " = ?", new String[]{String.valueOf(model.getId())});
        } finally {
            Close();
        }

        //se tem custos adicionais

        custoAdicionalDao.DeletarPorViagemId(model.getId());

        if (!model.getCustosAdicionais().isEmpty()) {
            custoAdicionalDao.Inserir(model.getCustosAdicionais(), model.getId());
        }
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

    public ArrayList<ViagemModel> ListarTodas(long usuarioId) {

        Open();

        ArrayList<ViagemModel> lista = new ArrayList<ViagemModel>();

        // select * from tb_produtos;
        Cursor c = db.query
                (
                        ViagemModel.TABELA_NOME,
                        colunas,
                        ViagemModel.COLUNA_USUARIO_ID + " = ?",
                        new String[]{String.valueOf(usuarioId)},
                        null,
                        null,
                        null
                );

        boolean existe = c.moveToFirst();

        if(!existe) return lista;

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

            lista.add(model);

            c.moveToNext();
        }

        c.close();

        for (ViagemModel viagem : lista){
            ArrayList<ViagemCustoAdicionalModel> custosAdicionais = custoAdicionalDao.ConsultarPorViagemId(viagem.getId());
            if(custosAdicionais == null || custosAdicionais.isEmpty()) continue;
            viagem.setCustosAdicionais(custosAdicionais);
        }

        Close();

        return lista;
    }

    public ViagemModel ConsultarPorId(long id) {
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

        boolean existe = c.moveToFirst();

        if(!existe) return null;

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

        c.close();

        model.setCustosAdicionais(custoAdicionalDao.ConsultarPorViagemId(id));

        Close();

        return model;
    }

}
