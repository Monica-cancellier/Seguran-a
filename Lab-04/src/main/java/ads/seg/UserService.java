package ads.seg;

import java.util.ArrayList;
import java.util.List;

    public class UserService {
        // Lista simulando o armazenamento de dados
        private List<User> users = new ArrayList<>();

        // 1. REGISTER
        public void register(String login, String password) throws UserAlreadyExistsException {
            // Regra: Não pode haver logins duplicados
            if (findByLogin(login) != null) {
                throw new UserAlreadyExistsException("O login '" + login + "' já existe no sistema.");
            }

            users.add(new User(login, password));
        }

        // 2. UPDATE PASSWORD
        public void updatePassword(String login, String currentPassword, String newPassword)
                throws UserNotFoundException, InvalidPasswordException {

            // Regra: O usuário precisa existir
            User user = findByLogin(login);
            if (user == null) {
                throw new UserNotFoundException("Usuário não encontrado.");
            }

            // Regra: A senha antiga deve ser validada
            if (!user.getSenha().equals(currentPassword)) {
                throw new InvalidPasswordException("A senha atual está incorreta.");
            }

            user.setSenha(newPassword);
        }

        // 3. AUTHENTICATE
        public void authenticate(String login, String password) throws InvalidLoginException {
            User user = findByLogin(login);

            // Regra: Credenciais devem coincidir
            if (user == null || !user.getSenha().equals(password)) {
                // Por segurança, não informamos se o erro foi no login ou na senha
                throw new InvalidLoginException("Login ou senha incorretos.");
            }

            System.out.println("Usuário autenticado com sucesso!");
        }

        // Método auxiliar privado para busca (reuso de código)
        private User findByLogin(String login) {
            return users.stream()
                    .filter(u -> u.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
        }
    }




