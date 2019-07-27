package com.urbainski.reservasapi.bo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.urbainski.reservasapi.entity.Reserva;

/**
 * 
 * @author Cristian Urbainski
 * @since 25/07/2019
 *
 */
@Service
public class CalcularValorReservaBO {

	private static final Double VALOR_DIARIA_NORMAL = 120.0;
	private static final Double VALOR_DIARIA_FIM_DE_SEMANA = 150.0;
	private static final Double VALOR_ESTACIONAMENTO_NORMAL = 15.0;
	private static final Double VALOR_ESTACIONAMENTO_FIM_DE_SEMANA = 20.0;

	private static final Double HORA_LIMITE_CHECK_OUT = 16.5;

	public Double calcularValor(Reserva reserva) {
		if (reserva == null) {
			throw new IllegalArgumentException("Parametro 'reserva' não pode ser nulo.");
		}

		double valorUltimaDiaria = 0.0;
		double valorUltimoEstacionamento = 0.0;

		int quantidadeDiariasNormais = 0;
		int quantidadeDiariasFimDeSemnana = 0;
		int quantidadeEstacionamentoNormais = 0;
		int quantidadeEstacionamentoFimDeSemnana = 0;

		boolean checkOutAposHoraLimite = false;

		long quantidadeDiasReserva = 1;
		if (reserva.getDataCheckOut() != null) {

			LocalTime timeStartDay = LocalTime.of(0, 0, 0);

			LocalDateTime dataCheckInCopy = LocalDateTime.of(reserva.getDataCheckIn().toLocalDate(), timeStartDay);
			LocalDateTime dataCheckOutCopy = LocalDateTime.of(reserva.getDataCheckOut().toLocalDate(), timeStartDay);

			quantidadeDiasReserva = ChronoUnit.DAYS.between(dataCheckInCopy, dataCheckOutCopy);

			double horaCheckOut = ((reserva.getDataCheckOut().getHour() * 60) + reserva.getDataCheckOut().getMinute())
					/ 60;
			checkOutAposHoraLimite = horaCheckOut >= HORA_LIMITE_CHECK_OUT;
		}

		LocalDateTime dataParaCalculo = LocalDateTime.of(reserva.getDataCheckIn().toLocalDate(),
				reserva.getDataCheckIn().toLocalTime());

		for (int i = 0; i < quantidadeDiasReserva; i++) {

			dataParaCalculo = dataParaCalculo.plusDays(1);

			DayOfWeek dayOfWeek = dataParaCalculo.getDayOfWeek();
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
				quantidadeDiariasFimDeSemnana++;
				if (reserva.getAdicionalVeiculo()) {
					quantidadeEstacionamentoFimDeSemnana++;
				}
				valorUltimaDiaria = VALOR_DIARIA_FIM_DE_SEMANA;
				valorUltimoEstacionamento = VALOR_ESTACIONAMENTO_FIM_DE_SEMANA;
			} else {
				quantidadeDiariasNormais++;
				if (reserva.getAdicionalVeiculo()) {
					quantidadeEstacionamentoNormais++;
				}
				valorUltimaDiaria = VALOR_DIARIA_NORMAL;
				valorUltimoEstacionamento = VALOR_ESTACIONAMENTO_NORMAL;
			}
		}

		Double valor = 0.0;
		valor += quantidadeDiariasNormais * VALOR_DIARIA_NORMAL;
		valor += quantidadeDiariasFimDeSemnana * VALOR_DIARIA_FIM_DE_SEMANA;
		valor += quantidadeEstacionamentoNormais * VALOR_ESTACIONAMENTO_NORMAL;
		valor += quantidadeEstacionamentoFimDeSemnana * VALOR_ESTACIONAMENTO_FIM_DE_SEMANA;

		if (checkOutAposHoraLimite) {
			valor += valorUltimaDiaria;
			if (reserva.getAdicionalVeiculo()) {
				valor += valorUltimoEstacionamento;
			}
		}

		return valor;
	}

}