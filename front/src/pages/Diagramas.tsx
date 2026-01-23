import React from 'react';
import estructura from '../assets/structura.png'

interface ArchitecturePattern {
  id: number;
  name: string;
  description: string;
  level: string;
  benefit: string;
}

const patterns: ArchitecturePattern[] = [
  {
    id: 1,
    name: "API Gateway / Reverse Proxy",
    description: "Nginx hace de puerta de entrada única para el frontend (y posiblemente APIs externas)",
    level: "Arquitectónico",
    benefit: "Simplifica el cliente, centraliza autenticación, rate limiting, logging, SSL termination, routing",
  },
  {
    id: 2,
    name: "Backend for Frontend (BFF) - variante ligera",
    description: "El frontend se comunica solo con Nginx → no llama directamente a múltiples APIs",
    level: "Arquitectónico",
    benefit: "Reduce complejidad en el cliente, optimiza respuestas para el frontend específico",
  },
  {
    id: 3,
    name: "Database per Service",
    description: "Cada microservicio tiene su propia base PostgreSQL (api-inventory-db y api-products-db)",
    level: "Arquitectónico",
    benefit: "Alta autonomía, evita acoplamiento, permite elegir DB por servicio si cambia en el futuro",
  },
  {
    id: 4,
    name: "Event-Driven Architecture",
    description: "Uso de Kafka para comunicación asíncrona entre servicios",
    level: "Arquitectónico",
    benefit: "Desacoplamiento temporal, escalabilidad, resiliencia ante fallos, procesamiento eventual consistente",
  },
  {
    id: 5,
    name: "Asynchronous Messaging",
    description: "Comunicación vía Kafka (en lugar de llamadas HTTP síncronas entre servicios)",
    level: "Integración",
    benefit: "Evita cascadas de fallos, permite picos de carga, reintentos automáticos, replay de eventos",
  },
  {
    id: 6,
    name: "Microservices Architecture",
    description: "Múltiples servicios independientes (frontend, api-inventory, api-products)",
    level: "Arquitectónico",
    benefit: "Escalabilidad independiente, despliegues independientes, equipos autónomos",
  },
  {
    id: 7,
    name: "Containerization + Orquestación implícita",
    description: "Todo en contenedores Docker (probablemente con Docker Compose o Kubernetes)",
    level: "Infra",
    benefit: "Portabilidad, consistencia en entornos, fácil escalado horizontal",
  },
];


export default function NotFound() {
  return (
    <div style={{display: "flex", flexDirection: "column", alignItems: "center", padding: "20px 30px"}}>
      

      <div className="max-w-5xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
        {/* Título principal */}
        <div className="login-card m-2">
          <div className="terminal-header">
            <div className="cyberpunk-container">
              <h1 className="text-3xl md:text-4xl font-bold text-[var(--accent)] tracking-wider font-mono">
                Arquitectura LINKTIC
              </h1>
              <p className="mt-3 text-lg text-[var(--text-secondary)]">
                Microservicios híbridos con API Gateway + Event-Driven + Database per Service
              </p>
            </div>
          </div>
        </div>

        <div style={{display: "flex"}}>
          <div className="login-card m-2" style={{width: "50%"}}>
              <p>
                La arquitectura representa un sistema típico de <strong>microservicios</strong> con los siguientes elementos clave:
              </p>
              <ul className="space-y-3 text-[var(--text-secondary)] ml-6 list-disc marker:text-[var(--accent)]">
                <li>
                  <strong className="text-[var(--accent-light)]">Nginx</strong> → actúa como <em>reverse proxy</em> / punto de entrada único para el frontend y posiblemente APIs externas.
                </li>
                <li>
                  Dos servicios backend principales: <strong className="text-[var(--primary-light)]">api-inventory</strong> y <strong className="text-[var(--primary-light)]">api-products</strong>, cada uno con su propia base PostgreSQL.
                </li>
                <li>
                  Comunicación <strong>síncrona</strong> → HTTP requests directos desde el frontend hacia las APIs.
                </li>
                <li>
                  Comunicación <strong>asíncrona</strong> → a través de <strong className="text-[var(--accent)]">Kafka</strong> (message broker para eventos).
                </li>
                <li>
                  Bases de datos separadas por servicio (<strong>Database-per-Service</strong>).
                </li>
              </ul>
          </div>
          <div style={{width: "50%", display: "flex", padding: "25px 10px"}}>
            <img src={estructura} alt="Not found" style={{width: "100%"}} />
          </div>
        </div>
        

        {/* Tabla de patrones */}
        <div className="overflow-x-auto">
          <table className="min-w-full border-collapse bg-[rgba(30,30,63,0.4)] backdrop-blur-lg rounded-xl overflow-hidden border border-[var(--border-light)]">
            <thead>
              <tr className="bg-[rgba(15,15,35,0.75)] border-b border-[var(--border)]">
                <th className="px-6 py-4 text-left text-xs font-semibold text-[var(--accent)] uppercase tracking-wider">
                  #
                </th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-[var(--accent)] uppercase tracking-wider">
                  Patrón de Diseño
                </th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-[var(--accent)] uppercase tracking-wider hidden md:table-cell">
                  Descripción
                </th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-[var(--accent)] uppercase tracking-wider">
                  Nivel
                </th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-[var(--accent)] uppercase tracking-wider hidden lg:table-cell">
                  Beneficio principal
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-[var(--border-light)]">
              {patterns.map((pattern) => (
                <tr
                  key={pattern.id}
                  className="hover:bg-[rgba(99,102,241,0.08)] transition-colors duration-150"
                >
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-[var(--text-muted)]">
                    {pattern.id}
                  </td>
                  <td className="px-6 py-4 text-sm font-medium text-[var(--text-primary)]">
                    {pattern.name}
                  </td>
                  <td className="px-6 py-4 text-sm text-[var(--text-secondary)] hidden md:table-cell">
                    {pattern.description}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-[var(--accent-light)]">
                    {pattern.level}
                  </td>
                  <td className="px-6 py-4 text-sm text-[var(--text-secondary)] hidden lg:table-cell">
                    {pattern.benefit}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}