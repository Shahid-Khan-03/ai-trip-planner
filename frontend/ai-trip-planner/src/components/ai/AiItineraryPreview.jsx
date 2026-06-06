
const AiItineraryPreview = ({ result }) => {
  if (!result) return null;

  return (
    <section className="card">
      <h3>AI Preview</h3>
      <pre className="preview-box">{JSON.stringify(result, null, 2)}</pre>
    </section>
  );
};

export default AiItineraryPreview;
