# 🩺 QHealth – Atendimento Finalizado Service  
Módulo responsável pelo gerenciamento de **atendimentos finalizados**, permitindo o controle completo de registros, atualizações e exclusões desses dados.

## 📝 Visão Geral  
O **QHealth – Atendimento Finalizado Service** fornece uma API REST robusta para lidar com todo o ciclo de vida de um atendimento já concluído.  
Este serviço é parte integrante do ecossistema QHealth, garantindo organização e rastreabilidade dos atendimentos finalizados em Unidades Básicas de Saúde ou clínicas.

Com endpoints bem estruturados, a aplicação permite operações completas de **CRUD** (criar, ler, atualizar e excluir) de atendimentos.

## 🛠 Funcionalidades  
- 🩺 **CRUD de Atendimentos Finalizados**: criação, listagem paginada, busca individual, atualização e exclusão.  
- ✅ **Validação de dados de entrada**: uso de anotações de validação para consistência das informações.  
- 📜 **Logs de operações**: registro detalhado de cada requisição (GET, POST, PUT, DELETE).  
- 🧪 **Testes unitários**: cobertura para garantir estabilidade e qualidade.

## 🚀 Tecnologias Principais  
- **Java Spring Boot** – API REST escalável e moderna.  
- **Spring Data JPA** – persistência e paginação dos registros de atendimento.  
- **Swagger/OpenAPI** – documentação interativa dos endpoints.  
- **JUnit** – criação e execução de testes automatizados.  
- **Docker** – containerização para deploy simplificado.
