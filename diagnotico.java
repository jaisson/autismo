/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package autismo;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge; // Usaremos para arestas simples por enquanto, mas veremos como personalizá-las

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jaisson.duarte
 */
public class Autismo {

// Classe para representar os Vértices (Nós) do nosso grafo
  static class Node {

    String id;
    String type; // Ex: Paciente, Sintoma, Fator_Risco, Comorbidade, Resultado_Teste, Diagnostico
    Map<String, String> attributes; // Atributos adicionais como categoria, status, etc.

    public Node(String id, String type) {
      this.id = id;
      this.type = type;
      this.attributes = new HashMap<>();
    }

    public Node(String id, String type, Map<String, String> attributes) {
      this.id = id;
      this.type = type;
      this.attributes = new HashMap<>(attributes);
    }

    public String getId() {
      return id;
    }

    public String getType() {
      return type;
    }

    public Map<String, String> getAttributes() {
      return attributes;
    }

    @Override
    public String toString() {
      return id + " (" + type + ")";
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return id.equals(node.id);
    }

    @Override
    public int hashCode() {
      return id.hashCode();
    }
  }

  // Classe para representar as Arestas (Relações) do nosso grafo
  static class RelationshipEdge extends DefaultEdge {

    String relation; // Ex: apresenta, tem_historico_de, sugere

    public RelationshipEdge(String relation) {
      this.relation = relation;
    }

    public String getRelation() {
      return relation;
    }

    @Override
    public String toString() {
      return "(" + getSource() + " -[" + relation + "]-> " + getTarget() + ")";
    }
  }

  public static void main(String[] args) {
    // Criar o grafo direcionado usando DefaultDirectedGraph
    // O primeiro tipo paramétrico é o tipo de vértice (Node)
    // O segundo tipo paramétrico é o tipo de aresta (RelationshipEdge)
    Graph<Node, RelationshipEdge> graph = new DefaultDirectedGraph<>(RelationshipEdge.class);

    // --- 2. Adicionar os nós (vértices) ---
    // Vamos criar um mapa para manter os nós acessíveis pelo ID, facilitando a criação de arestas
    Map<String, Node> nodes = new HashMap<>();

    // Pacientes
    nodes.put("Paciente_A", new Node("Paciente_A", "Paciente", Map.of("status", "Em Avaliação")));
    nodes.put("Paciente_B", new Node("Paciente_B", "Paciente", Map.of("status", "Em Avaliação")));
    nodes.put("Paciente_C", new Node("Paciente_C", "Paciente", Map.of("status", "Diagnosticado")));

    // Sintomas
    nodes.put("Comportamento_Repetitivo", new Node("Comportamento_Repetitivo", "Sintoma", Map.of("categoria", "Comportamental")));
    nodes.put("Dificuldade_Contato_Visual", new Node("Dificuldade_Contato_Visual", "Sintoma", Map.of("categoria", "Comportamental")));
    nodes.put("Interesses_Restritos", new Node("Interesses_Restritos", "Sintoma", Map.of("categoria", "Comportamental")));
    nodes.put("Hipossensibilidade_Auditiva", new Node("Hipossensibilidade_Auditiva", "Sintoma", Map.of("categoria", "Comportamental")));
    nodes.put("Atraso_Fala", new Node("Atraso_Fala", "Sintoma", Map.of("categoria", "Comunicacao")));
    nodes.put("Ecolalia", new Node("Ecolalia", "Sintoma", Map.of("categoria", "Comunicacao")));
    nodes.put("Dificuldade_Iniciar_Conversa", new Node("Dificuldade_Iniciar_Conversa", "Sintoma", Map.of("categoria", "Comunicacao")));
    nodes.put("Dificuldade_Interacao_Social", new Node("Dificuldade_Interacao_Social", "Sintoma", Map.of("categoria", "Social")));
    nodes.put("Falta_Empatia", new Node("Falta_Empatia", "Sintoma", Map.of("categoria", "Social")));
    nodes.put("Impulsividade", new Node("Impulsividade", "Sintoma", Map.of("categoria", "Comportamental")));

    // Fatores de Risco
    nodes.put("Historico_Familiar_Autismo", new Node("Historico_Familiar_Autismo", "Fator_Risco", Map.of("categoria", "Genetico")));
    nodes.put("Sindromes_Geneticas_Associadas", new Node("Sindromes_Geneticas_Associadas", "Fator_Risco", Map.of("categoria", "Genetico")));
    nodes.put("Prematuridade", new Node("Prematuridade", "Fator_Risco", Map.of("categoria", "Pre_Perinatal")));
    nodes.put("Peso_Baixo_Nascimento", new Node("Peso_Baixo_Nascimento", "Fator_Risco", Map.of("categoria", "Pre_Perinatal")));

    // Comorbidades
    nodes.put("TDAH", new Node("TDAH", "Comorbidade"));
    nodes.put("Ansiedade", new Node("Ansiedade", "Comorbidade"));
    nodes.put("Epilepsia", new Node("Epilepsia", "Comorbidade"));
    nodes.put("Deficiencia_Intelectual", new Node("Deficiencia_Intelectual", "Comorbidade"));

    // Resultados de Testes
    nodes.put("Resultado_ADOS_Alto", new Node("Resultado_ADOS_Alto", "Resultado_Teste", Map.of("teste", "ADOS")));
    nodes.put("Pontuacao_CARS_Moderada", new Node("Pontuacao_CARS_Moderada", "Resultado_Teste", Map.of("teste", "CARS")));
    nodes.put("Avaliacao_Linguagem_Prejudicada", new Node("Avaliacao_Linguagem_Prejudicada", "Resultado_Teste", Map.of("teste", "Linguagem")));

    // Diagnóstico
    nodes.put("Diagnostico_Autismo_Possivel", new Node("Diagnostico_Autismo_Possivel", "Diagnostico"));
    nodes.put("Diagnostico_Autismo_Confirmado", new Node("Diagnostico_Autismo_Confirmado", "Diagnostico"));

    // Adicionar todos os nós ao grafo
    for (Node node : nodes.values()) {
      graph.addVertex(node);
    }

    // --- 3. Adicionar as arestas (relações) ---
    // Helper para adicionar arestas de forma mais legível
    addEdge(graph, nodes, "Paciente_A", "Dificuldade_Interacao_Social", "apresenta");
    addEdge(graph, nodes, "Paciente_A", "Atraso_Fala", "apresenta");
    addEdge(graph, nodes, "Paciente_A", "Comportamento_Repetitivo", "apresenta");
    addEdge(graph, nodes, "Paciente_B", "Dificuldade_Contato_Visual", "apresenta");
    addEdge(graph, nodes, "Paciente_B", "Interesses_Restritos", "apresenta");
    addEdge(graph, nodes, "Paciente_C", "Ecolalia", "apresenta");
    addEdge(graph, nodes, "Paciente_C", "Hipossensibilidade_Auditiva", "apresenta");

    // Relações Paciente -> Fator de Risco
    addEdge(graph, nodes, "Paciente_A", "Historico_Familiar_Autismo", "tem_historico_de");
    addEdge(graph, nodes, "Paciente_B", "Prematuridade", "tem_historico_de");

    // Relações Paciente -> Comorbidade
    addEdge(graph, nodes, "Paciente_A", "Ansiedade", "tem_comorbidade");
    addEdge(graph, nodes, nodes.get("Paciente_B"), nodes.get("TDAH"), "tem_comorbidade"); // Outra forma de chamar

    // Relações Paciente -> Resultado de Teste
    addEdge(graph, nodes, "Paciente_A", "Resultado_ADOS_Alto", "obteve_resultado");
    addEdge(graph, nodes, "Paciente_B", "Pontuacao_CARS_Moderada", "obteve_resultado");
    addEdge(graph, nodes, "Paciente_C", "Avaliacao_Linguagem_Prejudicada", "obteve_resultado");

    // Relações Sintoma -> Sintoma (co-ocorrência)
    addEdge(graph, nodes, "Comportamento_Repetitivo", "Interesses_Restritos", "associado_a");
    addEdge(graph, nodes, "Atraso_Fala", "Dificuldade_Iniciar_Conversa", "associado_a");

    // Relações Sintoma -> Comorbidade
    addEdge(graph, nodes, "Impulsividade", "TDAH", "indicativo_de");

    // Relações Fator de Risco -> Sintoma
    addEdge(graph, nodes, "Prematuridade", "Atraso_Fala", "aumenta_risco_de");

    // Relações Resultado de Teste -> Diagnóstico
    addEdge(graph, nodes, "Resultado_ADOS_Alto", "Diagnostico_Autismo_Possivel", "sugere");
    addEdge(graph, nodes, "Pontuacao_CARS_Moderada", "Diagnostico_Autismo_Possivel", "sugere");
    addEdge(graph, nodes, "Diagnostico_Autismo_Possivel", "Diagnostico_Autismo_Confirmado", "pode_levar_a");

    // Exemplo de relação de inferência ou critério
    addEdge(graph, nodes, "Dificuldade_Interacao_Social", "Diagnostico_Autismo_Possivel", "criterio_para");
    addEdge(graph, nodes, "Atraso_Fala", "Diagnostico_Autismo_Possivel", "criterio_para");
    addEdge(graph, nodes, "Comportamento_Repetitivo", "Diagnostico_Autismo_Possivel", "criterio_para");
    addEdge(graph, nodes, "Historico_Familiar_Autismo", "Diagnostico_Autismo_Possivel", "aumenta_probabilidade");
    addEdge(graph, nodes, "Resultado_ADOS_Alto", "Diagnostico_Autismo_Confirmado", "forte_indicativo");

    // --- 4. Exemplo de como consultar o grafo ---
    System.out.println("\n--- Informações do Grafo ---");
    System.out.println("Número de Nós: " + graph.vertexSet().size());
    System.out.println("Número de Arestas: " + graph.edgeSet().size());

    // Vizinhos (sucessores) de um nó específico
    Node pacienteA = nodes.get("Paciente_A");
    if (pacienteA != null) {
      System.out.println("\nVizinhos de 'Paciente_A':");
      Set<RelationshipEdge> outgoingEdges = graph.outgoingEdgesOf(pacienteA);
      for (RelationshipEdge edge : outgoingEdges) {
        Node targetNode = graph.getEdgeTarget(edge);
        System.out.println("- " + targetNode.getId() + " (relação: " + edge.getRelation() + ")");
      }
    }
    /*
    // Nós de um tipo específico
    System.out.println("\nNós do tipo 'Sintoma':");
    for (Node node : graph.vertexSet()) {
      if (node.getType().equals("Sintoma")) {
        System.out.println("- " + node.getId() + " (Categoria: " + node.getAttributes().get("categoria") + ")");
      }
    }*/

    Node pacienteB = nodes.get("Paciente_B");
    if (pacienteB != null) {
      System.out.println("\nVizinhos de 'Paciente_B':");
      Set<RelationshipEdge> outgoingEdges = graph.outgoingEdgesOf(pacienteB);
      for (RelationshipEdge edge : outgoingEdges) {
        Node targetNode = graph.getEdgeTarget(edge);
        System.out.println("- " + targetNode.getId() + " (relação: " + edge.getRelation() + ")");
      }
    }

    Node pacienteC = nodes.get("Paciente_C");
    if (pacienteB != null) {
      System.out.println("\nVizinhos de 'Paciente_C':");
      Set<RelationshipEdge> outgoingEdges = graph.outgoingEdgesOf(pacienteC);
      for (RelationshipEdge edge : outgoingEdges) {
        Node targetNode = graph.getEdgeTarget(edge);
        System.out.println("- " + targetNode.getId() + " (relação: " + edge.getRelation() + ")");
      }
    }

    /*
    // Nós de um tipo específico
    System.out.println("\nNós do tipo 'Sintoma':");
    for (Node node : graph.vertexSet()) {
      if (node.getType().equals("Sintoma")) {
        System.out.println("- " + node.getId() + " (Categoria: " + node.getAttributes().get("categoria") + ")");
      }
    }*/
  }

  // Método helper para adicionar arestas de forma mais concisa
  private static void addEdge(Graph<Node, RelationshipEdge> graph, Map<String, Node> nodes,
          String sourceId, String targetId, String relation) {
    Node source = nodes.get(sourceId);
    Node target = nodes.get(targetId);
    if (source != null && target != null) {
      graph.addEdge(source, target, new RelationshipEdge(relation));
    } else {
      System.err.println("Erro: Nó(s) não encontrado(s) para a aresta: " + sourceId + " -> " + targetId);
    }
  }

  // Sobrecarga para quando já temos os objetos Node
  private static void addEdge(Graph<Node, RelationshipEdge> graph, Map<String, Node> nodes,
          Node source, Node target, String relation) {
    if (source != null && target != null) {
      graph.addEdge(source, target, new RelationshipEdge(relation));
    } else {
      System.err.println("Erro: Nó(s) nulo(s) para a aresta.");
    }
  }

}
