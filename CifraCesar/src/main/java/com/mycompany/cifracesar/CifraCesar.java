/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.cifracesar;

import java.util.HashMap;
import java.util.Map;

public class CifraCesar {

    // Método para criptografar uma mensagem usando a cifra de César
    public static String encripta(String mensagem, int k) {
        
        if (k < 1 || k > 25) {
            throw new IllegalArgumentException("A chave de deslocamento deve estar entre 1 e 25.");
        }
        StringBuilder mensagemCriptografada = new StringBuilder();

        // Percorre a mensagem
        for (char c : mensagem.toCharArray()) {        
            if (Character.isLetter(c)) {        
                int asciiOriginal = (int) c;
                int asciiBase = Character.isUpperCase(c) ? (int) 'A' : (int) 'a';
                int asciiModificado = (asciiOriginal - asciiBase + k) % 26 + asciiBase;
   
                mensagemCriptografada.append((char) asciiModificado);
            } else {
                // Se não for uma letra, adiciona o caractere sem modificação
                mensagemCriptografada.append(c);
            }
        }
        return mensagemCriptografada.toString();
    }

    // Método para calcular a frequência
    private static Map<Character, Double> calculaFrequencia(String mensagem) {
        Map<Character, Double> frequencia = new HashMap<>();
        int letrasTotais = 0;

        for (char c : mensagem.toLowerCase().toCharArray()) {
            if (Character.isLetter(c)) {
                frequencia.put(c, frequencia.getOrDefault(c, 0.0) + 1);
                letrasTotais++;
            }
        }
        for (char c : frequencia.keySet()) {
            frequencia.put(c, (frequencia.get(c) / letrasTotais) * 100);
        }

        return frequencia;
    }

    // Método para realizar a criptoanálise e encontrar a chave de deslocamento mais provável
    public static int analisa(String mensagemCriptografada) {
        Map<Character, Double> frequenciaMensagem = calculaFrequencia(mensagemCriptografada);

        double diferencaMinima = Double.MAX_VALUE;
        int melhor = 0;

        // Frequência típica de letras em textos em português
        final Map<Character, Double> FREQUENCIA_LETRA_PORTUGUES = new HashMap<>();
        FREQUENCIA_LETRA_PORTUGUES.put('a', 14.63);
        FREQUENCIA_LETRA_PORTUGUES.put('b', 1.04);
        FREQUENCIA_LETRA_PORTUGUES.put('c', 3.88);
        FREQUENCIA_LETRA_PORTUGUES.put('d', 4.99);
        FREQUENCIA_LETRA_PORTUGUES.put('e', 12.57);
        FREQUENCIA_LETRA_PORTUGUES.put('f', 1.02);
        FREQUENCIA_LETRA_PORTUGUES.put('g', 1.30);
        FREQUENCIA_LETRA_PORTUGUES.put('h', 0.73);
        FREQUENCIA_LETRA_PORTUGUES.put('i', 6.18);
        FREQUENCIA_LETRA_PORTUGUES.put('j', 0.40);
        FREQUENCIA_LETRA_PORTUGUES.put('k', 0.02);
        FREQUENCIA_LETRA_PORTUGUES.put('l', 2.78);
        FREQUENCIA_LETRA_PORTUGUES.put('m', 4.74);
        FREQUENCIA_LETRA_PORTUGUES.put('n', 4.63);
        FREQUENCIA_LETRA_PORTUGUES.put('o', 9.28);
        FREQUENCIA_LETRA_PORTUGUES.put('p', 2.52);
        FREQUENCIA_LETRA_PORTUGUES.put('q', 1.20);
        FREQUENCIA_LETRA_PORTUGUES.put('r', 6.53);
        FREQUENCIA_LETRA_PORTUGUES.put('s', 7.81);
        FREQUENCIA_LETRA_PORTUGUES.put('t', 4.34);
        FREQUENCIA_LETRA_PORTUGUES.put('u', 4.63);
        FREQUENCIA_LETRA_PORTUGUES.put('v', 1.67);
        FREQUENCIA_LETRA_PORTUGUES.put('w', 0.01);
        FREQUENCIA_LETRA_PORTUGUES.put('x', 0.21);
        FREQUENCIA_LETRA_PORTUGUES.put('y', 0.01);
        FREQUENCIA_LETRA_PORTUGUES.put('z', 0.47);

      
        for (int shift = 1; shift <= 25; shift++) {
            double diferenca = 0;
            for (char c : FREQUENCIA_LETRA_PORTUGUES.keySet()) {
                char caracterModificado = (char) ('a' + (c - 'a' + shift) % 26);
                double frequenciaEsperada = FREQUENCIA_LETRA_PORTUGUES.get(c);
                double frequenciaReal = frequenciaMensagem.getOrDefault(caracterModificado, 0.0) * (frequenciaEsperada / 100.0); // Correção aqui
                diferenca += Math.abs(frequenciaEsperada - frequenciaReal);
            }

            // Mantém a chave de deslocamento com a menor diferença
            if (diferenca < diferencaMinima) {
                diferencaMinima = diferenca;
                melhor = shift;
            }
        }

        return melhor;
    }

    public static void main(String[] args) {
        // Mensagem original
        String mensagemOriginal = "Exemplo de mensagem";
        System.out.println("Mensagem original: " + mensagemOriginal);

        // Chave de deslocamento para criptografia
        int chave = 3;

        // Criptografar a mensagem original
        String mensagemCriptografada = encripta(mensagemOriginal, chave);
        System.out.println("Mensagem criptografada: " + mensagemCriptografada);

        // Realiza a criptoanálise para encontrar a chave de deslocamento mais provável
        int bestShift = analisa(mensagemCriptografada);
        System.out.println("Chave de deslocamento mais provável: " + bestShift);
    }
}
