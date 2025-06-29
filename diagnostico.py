import networkx as nx
import matplotlib.pyplot as plt

# --- 1. Criar o grafo direcionado ---
# Usamos DiGraph para indicar que as relações podem ter um sentido (ex: Paciente 'apresenta' Sintoma)
G = nx.DiGraph()

# --- 2. Adicionar os nós (com atributos para categorização) ---

# Nós de Pacientes
G.add_node("Paciente_A", type="Paciente", status="Em Avaliação")
G.add_node("Paciente_B", type="Paciente", status="Em Avaliação")
G.add_node("Paciente_C", type="Paciente", status="Diagnosticado")

# Nós de Sintomas (comportamentais, comunicação, sociais)
# Comportamentais
G.add_node("Comportamento_Repetitivo", type="Sintoma", categoria="Comportamental")
G.add_node("Dificuldade_Contato_Visual", type="Sintoma", categoria="Comportamental")
G.add_node("Interesses_Restritos", type="Sintoma", categoria="Comportamental")
G.add_node("Hipossensibilidade_Auditiva", type="Sintoma", categoria="Comportamental")
# Comunicação
G.add_node("Atraso_Fala", type="Sintoma", categoria="Comunicacao")
G.add_node("Ecolalia", type="Sintoma", categoria="Comunicacao")
G.add_node("Dificuldade_Iniciar_Conversa", type="Sintoma", categoria="Comunicacao")
# Sociais
G.add_node("Dificuldade_Interacao_Social", type="Sintoma", categoria="Social")
G.add_node("Falta_Empatia", type="Sintoma", categoria="Social")
G.add_node("Impulsividade", type="Sintoma", categoria="Comportamental") # Pode ser associado a TDAH

# Nós de Fatores de Risco/Históricos
G.add_node("Historico_Familiar_Autismo", type="Fator_Risco", categoria="Genetico")
G.add_node("Sindromes_Geneticas_Associadas", type="Fator_Risco", categoria="Genetico")
G.add_node("Prematuridade", type="Fator_Risco", categoria="Pre_Perinatal")
G.add_node("Peso_Baixo_Nascimento", type="Fator_Risco", categoria="Pre_Perinatal")

# Nós de Comorbidades
G.add_node("TDAH", type="Comorbidade")
G.add_node("Ansiedade", type="Comorbidade")
G.add_node("Epilepsia", type="Comorbidade")
G.add_node("Deficiencia_Intelectual", type="Comorbidade")

# Nós de Resultados de Testes/Avaliações
G.add_node("Resultado_ADOS_Alto", type="Resultado_Teste", teste="ADOS")
G.add_node("Pontuacao_CARS_Moderada", type="Resultado_Teste", teste="CARS")
G.add_node("Avaliacao_Linguagem_Prejudicada", type="Resultado_Teste", teste="Linguagem")

# Nós de Diagnóstico (poderiam ser inferidos)
G.add_node("Diagnostico_Autismo_Possivel", type="Diagnostico")
G.add_node("Diagnostico_Autismo_Confirmado", type="Diagnostico")

# --- 3. Adicionar as arestas (relações) ---

# Relações Paciente -> Sintoma
G.add_edge("Paciente_A", "Dificuldade_Interacao_Social", relation="apresenta")
G.add_edge("Paciente_A", "Atraso_Fala", relation="apresenta")
G.add_edge("Paciente_A", "Comportamento_Repetitivo", relation="apresenta")
G.add_edge("Paciente_B", "Dificuldade_Contato_Visual", relation="apresenta")
G.add_edge("Paciente_B", "Interesses_Restritos", relation="apresenta")
G.add_edge("Paciente_C", "Ecolalia", relation="apresenta")
G.add_edge("Paciente_C", "Hipossensibilidade_Auditiva", relation="apresenta")

# Relações Paciente -> Fator de Risco
G.add_edge("Paciente_A", "Historico_Familiar_Autismo", relation="tem_historico_de")
G.add_edge("Paciente_B", "Prematuridade", relation="tem_historico_de")

# Relações Paciente -> Comorbidade
G.add_edge("Paciente_A", "Ansiedade", relation="tem_comorbidade")
G.add_edge("Paciente_B", "TDAH", relation="tem_comorbidade")

# Relações Paciente -> Resultado de Teste
G.add_edge("Paciente_A", "Resultado_ADOS_Alto", relation="obteve_resultado")
G.add_edge("Paciente_B", "Pontuacao_CARS_Moderada", relation="obteve_resultado")
G.add_edge("Paciente_C", "Avaliacao_Linguagem_Prejudicada", relation="obteve_resultado")

# Relações Sintoma -> Sintoma (co-ocorrência)
G.add_edge("Comportamento_Repetitivo", "Interesses_Restritos", relation="associado_a")
G.add_edge("Atraso_Fala", "Dificuldade_Iniciar_Conversa", relation="associado_a")

# Relações Sintoma -> Comorbidade
G.add_edge("Impulsividade", "TDAH", relation="indicativo_de")

# Relações Fator de Risco -> Sintoma
G.add_edge("Prematuridade", "Atraso_Fala", relation="aumenta_risco_de")

# Relações Resultado de Teste -> Diagnóstico
G.add_edge("Resultado_ADOS_Alto", "Diagnostico_Autismo_Possivel", relation="sugere")
G.add_edge("Pontuacao_CARS_Moderada", "Diagnostico_Autismo_Possivel", relation="sugere")
G.add_edge("Diagnostico_Autismo_Possivel", "Diagnostico_Autismo_Confirmado", relation="pode_levar_a")

# Exemplo de relação de inferência ou critério
G.add_edge("Dificuldade_Interacao_Social", "Diagnostico_Autismo_Possivel", relation="criterio_para")
G.add_edge("Atraso_Fala", "Diagnostico_Autismo_Possivel", relation="criterio_para")
G.add_edge("Comportamento_Repetitivo", "Diagnostico_Autismo_Possivel", relation="criterio_para")
G.add_edge("Historico_Familiar_Autismo", "Diagnostico_Autismo_Possivel", relation="aumenta_probabilidade")
G.add_edge("Resultado_ADOS_Alto", "Diagnostico_Autismo_Confirmado", relation="forte_indicativo")


# --- 4. Visualizar o grafo (opcional, requer matplotlib) ---

plt.figure(figsize=(15, 10))

# Definir um layout para os nós
pos = nx.spring_layout(G, k=0.7, iterations=50) # Melhor para grafos maiores

# Cores para os nós baseadas no tipo
node_colors = []
for node in G.nodes():
    if G.nodes[node]['type'] == 'Paciente':
        node_colors.append('skyblue')
    elif G.nodes[node]['type'] == 'Sintoma':
        node_colors.append('lightcoral')
    elif G.nodes[node]['type'] == 'Fator_Risco':
        node_colors.append('lightgreen')
    elif G.nodes[node]['type'] == 'Comorbidade':
        node_colors.append('plum')
    elif G.nodes[node]['type'] == 'Resultado_Teste':
        node_colors.append('gold')
    elif G.nodes[node]['type'] == 'Diagnostico':
        node_colors.append('salmon')
    else:
        node_colors.append('lightgray')

# Desenhar os nós
nx.draw_networkx_nodes(G, pos, node_color=node_colors, node_size=3000)

# Desenhar as arestas
nx.draw_networkx_edges(G, pos, width=1.0, alpha=0.7, edge_color='gray', arrows=True, arrowsize=20)

# Desenhar os rótulos dos nós
nx.draw_networkx_labels(G, pos, font_size=9, font_weight='bold')

# Desenhar os rótulos das arestas (relação)
edge_labels = nx.get_edge_attributes(G, 'relation')
nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels, font_color='darkgreen', font_size=7)

plt.title("Grafo de Modelo Abstrato para Diagnóstico de Autismo", size=15)
plt.axis('off') # Remover os eixos
plt.show()

# --- Exemplo de como consultar o grafo ---
print("\n--- Informações do Grafo ---")
print(f"Número de Nós: {G.number_of_nodes()}")
print(f"Número de Arestas: {G.number_of_edges()}")

# Vizinhos de um nó específico
print("\nVizinhos de 'Paciente_A':")
for neighbor in G.successors("Paciente_A"):
    print(f"- {neighbor} (relação: {G.edges['Paciente_A', neighbor]['relation']})")

print("\nNós do tipo 'Sintoma':")
for node, data in G.nodes(data=True):
    if data['type'] == 'Sintoma':
        print(f"- {node} (Categoria: {data['categoria']})")
