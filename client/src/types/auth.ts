export type AuthorizationResponse = {
  error?: string;
  authorization: Authorization;
};
export type Authorization = {
  token?: string;
  user?: {
    firstName?: string;
    lastName?: string;
  };
};
