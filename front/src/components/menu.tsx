import { Link } from 'react-router-dom'

const NavBar = () => {
  return (
    <nav>
      <Link
        to="/"
      >
        Home
      </Link>

      <Link
        to="/inventory"
      >
        APP
      </Link>

      <Link
        to="/diagramas"
      >
        Documentacion
      </Link>
    </nav>
  )
}

export default NavBar
