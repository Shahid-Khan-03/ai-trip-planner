

const AiGenerateButton = ({ loading = false, disabled = false, onClick }) => (
  <button className="button ghost" type="button" disabled={disabled || loading} onClick={onClick}>
    {loading ? 'Generating...' : 'Generate AI Plan'}
  </button>
);

export default AiGenerateButton;
