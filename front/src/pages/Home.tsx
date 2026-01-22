import React, { useEffect, useRef, useState } from "react";
import mermaid from "mermaid";

const Home: React.FC = () => {
  const loginFormRef = useRef<HTMLDivElement>(null);
  const sentinelRef = useRef<HTMLDivElement>(null);
  const scrollArrowRef = useRef<HTMLParagraphElement>(null);

  const [formHasOverflow, setFormHasOverflow] = useState(false);

  // Inicializar mermaid
  useEffect(() => {
    mermaid.initialize({ startOnLoad: true, theme: "dark" });
  }, []);

  // 1. Efecto de focus en inputs
  useEffect(() => {
    const inputs = document.querySelectorAll(".form-input");
    const handleFocus = (e: Event) => {
      const target = e.target as HTMLElement;
      const parent = target.parentElement;
      if (parent) parent.style.transform = "scale(1.02)";
    };
    const handleBlur = (e: Event) => {
      const target = e.target as HTMLElement;
      const parent = target.parentElement;
      if (parent) parent.style.transform = "scale(1)";
    };

    inputs.forEach((input) => {
      input.addEventListener("focus", handleFocus);
      input.addEventListener("blur", handleBlur);
    });

    return () => {
      inputs.forEach((input) => {
        input.removeEventListener("focus", handleFocus);
        input.removeEventListener("blur", handleBlur);
      });
    };
  }, []);

  // 2. Efecto visual al teclear en inputs (keyboard sound simulation)
  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if ((e.target as HTMLElement).matches(".form-input")) {
        const target = e.target as HTMLElement;
        target.style.boxShadow =
          "0 0 25px rgba(0, 255, 255, 0.5), inset 0 0 25px rgba(0, 255, 255, 0.1)";
        setTimeout(() => {
          target.style.boxShadow = "";
        }, 100);
      }
    };

    document.addEventListener("keydown", handleKeyDown);
    return () => document.removeEventListener("keydown", handleKeyDown);
  }, []);

  // 3. Efecto ripple en el botón
  // const handleButtonClick = (e: React.MouseEvent<HTMLButtonElement>) => {
  //   const btn = e.currentTarget;
  //   const ripple = document.createElement("div");
  //   ripple.className = "ripple-effect"; // puedes definir esta clase en CSS
  //   ripple.style.position = "absolute";
  //   ripple.style.borderRadius = "50%";
  //   ripple.style.background = "rgba(0, 255, 255, 0.5)";
  //   ripple.style.transform = "scale(0)";
  //   ripple.style.left = "50%";
  //   ripple.style.top = "50%";
  //   ripple.style.width = "20px";
  //   ripple.style.height = "20px";
  //   ripple.style.marginLeft = "-10px";
  //   ripple.style.marginTop = "-10px";
  //   ripple.style.pointerEvents = "none";

  //   btn.appendChild(ripple);

  //   // Forzamos reflow para que la animación comience
  //   ripple.offsetHeight;
  //   ripple.style.animation = "ripple 0.6s linear";

  //   setTimeout(() => ripple.remove(), 600);
  // };

  // 4. Lógica de flecha de scroll + IntersectionObserver
  useEffect(() => {
    const form = loginFormRef.current;
    const sentinel = sentinelRef.current;
    const arrow = scrollArrowRef.current;

    if (!form || !sentinel || !arrow) return;

    const updateArrow = () => {
      const hasOverflow = form.scrollHeight > form.clientHeight + 1;
      setFormHasOverflow(hasOverflow);
      if (!hasOverflow) {
        arrow.classList.add("scroll-hidden");
      }

      formHasOverflow;
    };

    const io = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.target === sentinel) {
            if (entry.isIntersecting) {
              arrow.classList.add("scroll-hidden");
            } else {
              updateArrow();
            }
          }
        });
      },
      { root: form, threshold: 0.99 }
    );

    io.observe(sentinel);

    const handleScroll = () => {
      const atBottom =
        Math.ceil(form.scrollTop + form.clientHeight) >= form.scrollHeight;
      if (atBottom) {
        arrow.classList.add("scroll-hidden");
      } else {
        updateArrow();
      }
    };

    form.addEventListener("scroll", handleScroll);
    window.addEventListener("resize", updateArrow);

    // Inicial
    updateArrow();
    if (document.fonts?.ready) {
      document.fonts.ready.then(updateArrow);
    }
    const timer = setTimeout(updateArrow, 100);

    return () => {
      io.disconnect();
      form.removeEventListener("scroll", handleScroll);
      window.removeEventListener("resize", updateArrow);
      clearTimeout(timer);
    };
  }, []);

  // 5. Efectos en checkboxes
  useEffect(() => {
    const checkboxes = document.querySelectorAll(".cyberpunk-checkbox");

    const handleChange = (e: Event) => {
      const checkbox = e.target as HTMLInputElement;
      const controlItem = checkbox.closest(".control-item") as HTMLElement;
      if (!controlItem) return;

      if (checkbox.checked) {
        controlItem.style.background =
          "linear-gradient(89deg, #5d0330 0%, #026565 100%)";
      } else {
        controlItem.style.background =
          "linear-gradient(90deg, rgba(0, 255, 255, 0.1) 0%, rgba(255, 0, 255, 0.1) 100%)";
      }
    };

    checkboxes.forEach((cb) => {
      cb.addEventListener("change", handleChange);
    });

    return () => {
      checkboxes.forEach((cb) => {
        cb.removeEventListener("change", handleChange);
      });
    };
  }, []);

  // 6. Parpadeo aleatorio de indicadores activos
  useEffect(() => {
    const interval = setInterval(() => {
      const activeIndicators = document.querySelectorAll(
        ".cyberpunk-checkbox:checked ~ .status-indicator"
      );
      activeIndicators.forEach((indicator) => {
        if (Math.random() > 0.7) {
          (indicator as HTMLElement).style.opacity = "0.3";
          setTimeout(() => {
            (indicator as HTMLElement).style.opacity = "1";
          }, 100);
        }
      });
    }, 2000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="d-flex justify-content-center">
      <div className="content-img-header">
        <img
          src="https://img.shields.io/badge/docker-257bd6?style=for-the-badge&logo=docker&logoColor=white"
          alt="Docker"
        />
        <img
          src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white"
          alt="Java"
        />
        <img
          src="https://img.shields.io/badge/springboot-000000?style=for-the-badge&logo=springboot&logoColor=green"
          alt="Spring Boot"
        />
        <img
          src="https://img.shields.io/badge/-ReactJs-61DAFB?logo=react&logoColor=white&style=for-the-badge"
          alt="React"
        />
        <img
          src="https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white"
          alt="Kafka"
        />
        <img
          src="https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white"
          alt="Postgres"
        />
        <img
          src="https://img.shields.io/badge/Nginx-009639?logo=nginx&logoColor=white&style=for-the-badge"
          alt="Nginx"
        />
      </div>
      <div className="login-card m-2">
				<div className="terminal-header">
          <div className="cyberpunk-container">
            <h1 className="neural-title">🏗️ Prueba Técnica - Microservicios</h1>
            <div className="scanlines"></div>
          </div>
          <p className="terminal-subtitle sentex">React + Spring Boot + Kafka + Postgres + Docker - Web Service - v2.77.1 -</p>
        </div>
      </div>

      {/* Diagrama */}
      <div>
        <h2>🧩 Diagrama de Arquitectura</h2>
        <div className="mermaid">
          {`
            flowchart LR
              User[Usuario] --> Nginx[Frontend]
              Nginx --> APIProd[API_Productos]
              Nginx --> APIInv[API_Inventory]

              APIProd --> Postgres[Postgres_product_db]
              APIInv --> PostgresInv[Postgres_inventory_db]

              APIInv --> Kafka[Kafka]
              Kafka --> Zookeeper[Zookeeper]
          `}
        </div>
      </div>

      {/* Inicio rápido */}
      <div>
        <h2>⚡ Inicio Rápido</h2>
        <p>
          Sigue estos pasos para tener el proyecto corriendo en pocos minutos
          usando <strong>Docker</strong>.
        </p>

        <h3>Requisitos previos</h3>
        <ul>
          <li>Docker + Docker Compose instalados</li>
          <li>
            Verificar: <code>docker --version</code> y{" "}
            <code>docker compose version</code>
          </li>
        </ul>

        <h3>Pasos</h3>
        <ol>
          <li>
            <strong>Crear carpeta</strong>
            <pre>
              <code>mkdir linktic{`\n`}cd linktic</code>
            </pre>
          </li>
          <li>
            <strong>Clonar repositorio</strong>
            <pre>
              <code>
                git clone https://github.com/HellFramco/linktic.git .
              </code>
            </pre>
          </li>
          <li>
            <strong>Construir imágenes</strong>
            <pre>
              <code>docker compose build</code>
            </pre>
          </li>
          <li>
            <strong>Levantar servicios</strong>
            <pre>
              <code>docker compose up -d</code>
            </pre>
            <p>
              <em>→ Esperar ~5 minutos la primera vez</em>
            </p>
          </li>
        </ol>
      </div>

      {/* Estructura */}
      <div>
        <h2>📁 Estructura del Repositorio</h2>
        <pre>
          <code>
            LINKTIC{`\n`}
            │{`\n`}
            ├─ .gitignore{`\n`}
            ├─ docker-compose.yml{`\n`}
            │{`\n`}
            ├─ back/{`\n`}
            │  ├─ db/init.sql{`\n`}
            │  ├─ inventory/     # Microservicio Inventory{`\n`}
            │  └─ products/      # Microservicio Productos{`\n`}
            │{`\n`}
            └─ front/            # Frontend React + Vite + Nginx
          </code>
        </pre>
      </div>

      {/* Contenedores */}
      <div>
        <h2>🐳 Contenedores (docker-compose.yml)</h2>
        <table>
          <thead>
            <tr>
              <th>Servicio</th>
              <th>Contenedor</th>
              <th>Puerto</th>
              <th>Rol</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>PostgreSQL</td>
              <td>postgres-db</td>
              <td>5433</td>
              <td>Base de datos</td>
            </tr>
            <tr>
              <td>API Productos</td>
              <td>api-productos</td>
              <td>8081</td>
              <td>Microservicio productos</td>
            </tr>
            <tr>
              <td>API Inventory</td>
              <td>api-inventory</td>
              <td>8082</td>
              <td>Microservicio inventario</td>
            </tr>
            <tr>
              <td>Kafka</td>
              <td>kafka</td>
              <td>9092</td>
              <td>Broker de eventos</td>
            </tr>
            <tr>
              <td>Zookeeper</td>
              <td>zookeeper</td>
              <td>2181</td>
              <td>Coordinador Kafka</td>
            </tr>
            <tr>
              <td>Frontend</td>
              <td>nginx-frontend</td>
              <td>80</td>
              <td>Servidor web (React)</td>
            </tr>
          </tbody>
        </table>
      </div>

      {/* Servicios */}
      <div>
        <h2>⚙️ Servicios Principales</h2>
        <ul>
          <li>
            <strong>Frontend</strong> → http://localhost
          </li>
          <li>
            <strong>API Productos</strong> → http://localhost:8081
          </li>
          <li>
            <strong>API Inventory</strong> → http://localhost:8082
          </li>
          <li>PostgreSQL → puerto 5433 (externo)</li>
        </ul>
      </div>

      {/* Inventory & Products */}
      <div>
        <h2>📦 Inventory Service</h2>
        <p>Microservicio de gestión de inventario y flujo de compra.</p>
        <ul>
          <li>
            Endpoints principales: <code>GET /inventory/{`{id}`}</code>,{" "}
            <code>POST /purchases</code>
          </li>
          <li>Seguridad: <code>X-API-KEY</code></li>
          <li>Consume eventos de Kafka</li>
        </ul>

        <h2>📦 Products Service</h2>
        <p>Microservicio de catálogo de productos.</p>
        <ul>
          <li>
            Endpoints principales: <code>GET /products</code>,{" "}
            <code>POST /products</code>
          </li>
          <li>Seguridad: <code>X-API-KEY</code></li>
        </ul>
      </div>

      {/* IA */}
      <div>
        <h2>🤖 Uso de IA en el desarrollo</h2>
        <ul>
          <li>
            <strong>ChatGPT</strong>: arquitectura inicial, estructura,
            buenas prácticas
          </li>
          <li>
            <strong>Grok</strong>: diagramas, documentación, limpieza de código,
            esquemas, semántica
          </li>
        </ul>
      </div>

      <div>
        <p>Proyecto de prueba técnica • HellFramco • 2025–2026</p>
        <p>github.com/HellFramco/linktic</p>
      </div>
    </div>
  );
};

export default Home;
